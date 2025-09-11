package com.jpd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.jpd.model.Customer;
import com.jpd.model.RememberWord;
import com.jpd.service.DirectionService;

@RestController
@RequestMapping("/upDirect")
public class DirectionController {

    @Autowired
    private DirectionService directionService;

    // Thêm từ mới (sử dụng @AuthenticationPrincipal để lấy email người dùng hiện tại)
    @PostMapping()
    public ResponseEntity<String> addWord(@RequestBody RememberWord entity, @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");

        boolean added = directionService.upNewWord(entity, email);
        if (added) {
            return ResponseEntity.ok("Đã thêm từ thành công");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể thêm từ mới (vượt quá giới hạn hoặc lỗi)");
        }
    }

    // Lấy danh sách từ đã lưu
    @GetMapping("/getWords")
    public ResponseEntity<List<RememberWord>> retrieveAllWord(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");

        List<RememberWord> list = directionService.retrieveAllWord(email);
        if (list == null || list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(list);
    }

    // Xóa từ theo id
    @DeleteMapping("/deleteWord")
    public ResponseEntity<String> deleteWord(@RequestParam("id") long id, @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        long result = directionService.deleteWordById(id, email);

        if (result == -1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không xác định được người dùng");
        } else if (result == -2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể cập nhật giới hạn từ");
        } else if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy từ cần xóa");
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
