package com.example.visioners.controller;

import com.example.visioners.service.PostService;
import com.example.visioners.dto.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    private final PostService postService;

    @Autowired
    public SearchController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/search")
    public String searchPosts(@RequestParam String searchBy, @RequestParam String keyword, Model model) {
        List<Post> searchResults = new ArrayList<>();  // 빈 리스트로 초기화

        if ("title".equals(searchBy)) {
            searchResults = postService.findByTitleContaining(keyword);
        } else if ("author".equals(searchBy)) {
            searchResults = postService.findByAuthorContaining(keyword);
        } else {
            searchResults = postService.getAllPosts();  // 예외 처리: 잘못된 검색 조건
        }

        model.addAttribute("posts", searchResults);
        return "board/search-results";  // 검색 결과를 보여줄 템플릿의 이름
    }

}