package com.example.visioners.controller;

import com.example.visioners.service.PostService;
import com.example.visioners.dto.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        List<Post> searchResults;

        if ("title".equals(searchBy)) {
            searchResults = postService.findByTitleContaining(keyword);
        } else if ("author".equals(searchBy)) {
            searchResults = postService.findByAuthorContaining(keyword);
        } else {
            // 검색 조건이 잘못된 경우, 예외처리 또는 기본적으로 모든 게시물을 가져오도록 설정할 수 있습니다.
            searchResults = postService.getAllPosts();
        }

        model.addAttribute("posts", searchResults);
        return "search-results"; // 검색 결과를 보여줄 템플릿의 이름
    }
}
