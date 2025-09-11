package com.jpd.service;

import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpd.model.SemanticResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class OpenAIService {
    
    @Value("${openai.api.key}")
    private String openaiApiKey;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public OpenAIService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }
    
    public String speechToText(File audioFile, String language) throws Exception {
        String url = "https://api.openai.com/v1/audio/transcriptions";
        
        // Tạo headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openaiApiKey);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        
        // Tạo body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(audioFile));
        body.add("model", "whisper-1");
        
        // Xử lý language parameter
        if (language != null && !language.isEmpty()) {
            body.add("language", language);
            log.info("Sử dụng ngôn ngữ: {}", language);
        } else {
            // Không set language để Whisper tự detect
            log.info("Để Whisper tự động nhận dạng ngôn ngữ");
        }
        
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        
        // Gửi request
        log.info("Đang gửi request lên OpenAI Whisper...");
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        
        // Parse response để lấy text
        JsonNode jsonResponse = objectMapper.readTree(response.getBody());
        String transcribedText = jsonResponse.get("text").asText();
        
        log.info("Nhận được kết quả từ Whisper: {}", transcribedText);
        return transcribedText;
    }
    
    // Overload method để backward compatibility
    public String speechToText(File audioFile) throws Exception {
        return speechToText(audioFile, null);
    }
    
    public SemanticResult compareSemanticWithEmbedding(String transcribedText, String expectedAnswer) throws Exception {
        // 1. Lấy embedding cho cả 2 câu
        double[] embedding1 = getTextEmbedding(transcribedText);
        double[] embedding2 = getTextEmbedding(expectedAnswer);
        
        // 2. Tính cosine similarity
        double similarity = calculateCosineSimilarity(embedding1, embedding2);
        
        // 3. Đánh giá kết quả
        boolean isCorrect = similarity >= 0.85; // Ngưỡng 85% cho semantic
        String feedback = generateSemanticFeedback(similarity);
        
        return SemanticResult.builder()
            .userAnswer(transcribedText)
            .expectedAnswer(expectedAnswer)
            .similarityScore(similarity)
            .match(isCorrect)
            .feedback(feedback)
            .build();
    }
    
    private double[] getTextEmbedding(String text) throws Exception {
        String url = "https://api.openai.com/v1/embeddings";
        
        // Tạo headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openaiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Tạo request body
        String requestBody = String.format("""
            {
                "input": "%s",
                "model": "text-embedding-3-small"
            }
            """, text.replace("\"", "\\\""));
        
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        
        // Gửi request
        log.info("Đang lấy embedding cho text: {}", text);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        
        // Parse response
        JsonNode jsonResponse = objectMapper.readTree(response.getBody());
        JsonNode embeddingArray = jsonResponse.get("data").get(0).get("embedding");
        
        // Convert to double array
        double[] embedding = new double[embeddingArray.size()];
        for (int i = 0; i < embeddingArray.size(); i++) {
            embedding[i] = embeddingArray.get(i).asDouble();
        }
        
        log.info("Đã lấy embedding thành công, kích thước: {}", embedding.length);
        return embedding;
    }
    
    private double calculateCosineSimilarity(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        
        double similarity = dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
        log.info("Cosine similarity: {}", similarity);
        return similarity;
    }
    
    private String generateSemanticFeedback(double similarity) {
        if (similarity >= 0.95) {
            return "Xuất sắc! Ý nghĩa hoàn toàn chính xác.";
        } else if (similarity >= 0.85) {
            return "Rất tốt! Ý nghĩa câu nói đúng.";
        } else if (similarity >= 0.70) {
            return "Khá tốt! Ý nghĩa gần đúng nhưng có thể cải thiện.";
        } else if (similarity >= 0.50) {
            return "Cần cải thiện. Ý nghĩa chưa hoàn toàn chính xác.";
        } else {
            return "Hãy thử lại. Ý nghĩa câu nói chưa đúng.";
        }
    }
}