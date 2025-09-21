package com.jpd.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.jpd.model.Customer;

import com.jpd.model.SemanticResult;
import com.jpd.model.TypeOfContent;
import com.jpd.repository.CustomerRepository;
import com.jpd.service.AudioService;

import com.jpd.service.OpenAIService;


import jakarta.websocket.server.PathParam;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET})
@RestController
@RequestMapping("/api")
@Slf4j
public class ListeningPageController {
    
    private final AudioService audioService;
    private final OpenAIService openAIService;

    public ListeningPageController(AudioService audioService, OpenAIService openAIService) {
 
        this.audioService = audioService;
        this.openAIService = openAIService;
    }
    
    @PostMapping("/evaluate")
    public ResponseEntity<SemanticResult> evaluateAnswer(@RequestParam("questionid")long id,
    		@RequestParam("audio") MultipartFile file,
                                                       @RequestParam("sentence") String expectedAnswer,
                                                     @RequestParam(value = "language", required = false) String language,@AuthenticationPrincipal Jwt jwt) {
    	 String email=jwt.getClaimAsString("email");
 

        // check customer vs question còn bao nhiêu request
//    	if(rq==null) {
//    		return ResponseEntity.badRequest()
//    	            .body(SemanticResult.error("Không còn lượt request hoặc chưa đến ngày reset"));
//    	}
//    	
    	try {
            // 1. Lưu file audio tạm thời
            File tempFile = audioService.saveTemporaryFile(file);
            
            // 2. Gửi lên OpenAI để nhận diện giọng nói (với language parameter)
            String transcribedText = openAIService.speechToText(tempFile, language);
            
            // 3. So sánh semantic bằng OpenAI Embedding
            SemanticResult result = openAIService.compareSemanticWithEmbedding(transcribedText, expectedAnswer);
            
            // 4. Xóa file tạm
            audioService.deleteTemporaryFile(tempFile);
          
            // 5. Trả về kết quả so sánh
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("Lỗi khi xử lý audio: ", e);
            return ResponseEntity.internalServerError().body(
                SemanticResult.error("Lỗi: " + e.getMessage())
            );
        }
    	
    	
    }
}
