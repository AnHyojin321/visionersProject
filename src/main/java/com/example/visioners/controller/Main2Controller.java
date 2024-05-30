package com.example.visioners.controller;

import com.example.visioners.service.SignUpService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class Main2Controller {

    private final SignUpService signUpService;

    @Autowired
    public Main2Controller(SignUpService signUpService) {
        this.signUpService = signUpService;
    }


    @GetMapping("/home")
    public String startHomePage() {
        return "start";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // login.html 파일을 반환
    }

    @GetMapping("/main")
    public String mainHomePage() {
        return "main"; // main.html 파일을 반환
    }


    @GetMapping("/signup")
    public String signupForm() {
        return "signup"; // signup.html 파일을 반환
    }

    @PostMapping("/signup")
    public String saveUser(@RequestParam String userid,
                           @RequestParam String userpassword,
                           @RequestParam String username,
                           @RequestParam String phone,
                           @RequestParam String email) {
        signUpService.saveSignUp(userid, userpassword, username, phone, email);
        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리디렉션
    }

}