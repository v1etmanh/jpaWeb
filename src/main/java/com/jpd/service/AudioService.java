package com.jpd.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class AudioService {
    
    public File saveTemporaryFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File audio trống");
        }
        
        // Tạo file tạm thời
        String extension = getFileExtension(file.getOriginalFilename());
        File tempFile = File.createTempFile("audio_", extension);
        
        // Lưu file
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        }
        
        log.info("Đã lưu file tạm: {}", tempFile.getAbsolutePath());
        return tempFile;
    }
    
    public void deleteTemporaryFile(File file) {
        if (file != null && file.exists()) {
            boolean deleted = file.delete();
            log.info("Xóa file tạm {}: {}", file.getName(), deleted ? "thành công" : "thất bại");
        }
    }
    
    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf('.') == -1) {
            return ".wav"; // Default extension
        }
        return filename.substring(filename.lastIndexOf('.'));
    }
}