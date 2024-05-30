package com.example.visioners.controller;

import com.example.visioners.dto.UserIdRequest;
import com.example.visioners.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/check-username")
    public ResponseEntity<String> checkUsername(@RequestBody UserIdRequest request) {
        String userid = request.getUserid();
        boolean exists = signUpService.checkIfUserExists(userid);
        if (exists) {
            return ResponseEntity.ok("사용할 수 없는 아이디입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 아이디입니다.");
        }
    }
}