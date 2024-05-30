package com.example.visioners.controller;

import com.example.visioners.service.SignUpService;
<<<<<<< HEAD
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

=======
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
>>>>>>> 8a650a7a5806a17b08f4e3c2898fc74ec3888455

    @GetMapping("/home")
    public String startHomePage() {
        return "start";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // login.html 파일을 반환
    }

<<<<<<< HEAD
=======

>>>>>>> 8a650a7a5806a17b08f4e3c2898fc74ec3888455
    @GetMapping("/main")
    public String mainHomePage() {
        return "main"; // main.html 파일을 반환
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup"; // signup.html 파일을 반환
    }

    @PostMapping("/signup")
<<<<<<< HEAD
    public String saveUser(@RequestParam String userid,
                           @RequestParam String userpassword,
                           @RequestParam String username,
                           @RequestParam String phone,
                           @RequestParam String email) {
        signUpService.saveSignUp(userid, userpassword, username, phone, email);
        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리디렉션
    }

=======
    public ModelAndView saveUser(@RequestParam String username,
                                 @RequestParam String userpassword,
                                 @RequestParam String email,
                                 @RequestParam String phone) {

        signUpService.saveSignUp(username, userpassword, email, phone);
        logger.info("User registered: {}", username);

        // 저장 후에 회원가입 완료 알림창과 함께 로그인 페이지로 리다이렉트
        return new ModelAndView("redirect:/login?success");
    }
>>>>>>> 8a650a7a5806a17b08f4e3c2898fc74ec3888455
}
