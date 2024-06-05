package com.example.visioners.controller;
import com.example.visioners.dto.Post;
import com.example.visioners.repository.PostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MyPageController {

    @GetMapping("/mypage/mypagemain")
    public String myPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        model.addAttribute("username", username);
        return "mypage/mypagemain";
    }

    private final PostRepository postRepository;

    public MyPageController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/mypost")
    public String myPosts(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName(); // 로그인한 사용자 이름 가져오기

        // 사용자 이름을 통해 게시글 검색
        List<Post> myPosts = postRepository.findByAuthor(currentUserName);
        model.addAttribute("posts", myPosts);

        return "mypage/mypost";
    }
}

