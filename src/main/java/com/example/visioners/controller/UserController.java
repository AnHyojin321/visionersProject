package com.example.visioners.controller;

import com.example.visioners.dto.User;
import com.example.visioners.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/mypage")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userinfo")
    public String userinfo(Model model, Principal principal) {
        String userid = principal.getName(); // SecurityContext에서 userid를 가져옴
        User user = userService.findByUserid(userid);

        if (user == null) {
            model.addAttribute("errorMessage", "유저 정보를 찾을 수 없습니다.");
            return "error-page"; // 에러 페이지로 리다이렉트
        }

        model.addAttribute("userid", user.getUserid());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("phone", user.getPhone());

        return "mypage/userinfo";
    }
}
