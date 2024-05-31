package com.example.visioners.controller;

import com.example.visioners.service.SignUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Main2Controller {
    private static final Logger logger = LoggerFactory.getLogger(Main2Controller.class);

    @Autowired
    private SignUpService signUpService;

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
    public ModelAndView saveUser(@RequestParam String username,
                                 @RequestParam String userpassword,
                                 @RequestParam String email,
                                 @RequestParam String phone) {

        signUpService.saveSignUp(username, userpassword, email, phone);
        logger.info("User registered: {}", username);

        // 저장 후에 회원가입 완료 알림창과 함께 로그인 페이지로 리다이렉트
        return new ModelAndView("redirect:/login?success");
    }
}
