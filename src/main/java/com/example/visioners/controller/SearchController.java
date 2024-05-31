package com.example.visioners.controller;

import com.example.visioners.dto.Post;
import com.example.visioners.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private PostService postService;

    @GetMapping("/search")
    public String search(@RequestParam("searchBy") String searchBy,
                         @RequestParam("keyword") String keyword,
                         Model model,
                         @PageableDefault(size = 10) Pageable pageable) {
        Page<Post> searchResults = Page.empty();
        try {
            logger.debug("SearchController: searchBy = {}, keyword = {}", searchBy, keyword);
            if ("title".equalsIgnoreCase(searchBy)) {
                searchResults = postService.findByTitleContaining(keyword, pageable);
            } else if ("author".equalsIgnoreCase(searchBy)) {
                searchResults = postService.findByAuthorContaining(keyword, pageable);
            } else {
                logger.warn("Invalid searchBy value: {}", searchBy);
                model.addAttribute("error", "Invalid search criteria");
                return "errorPage";  // 예외 처리: 잘못된 검색 조건
            }
            model.addAttribute("posts", searchResults);
        } catch (Exception e) {
            logger.error("Error during search: ", e);
            model.addAttribute("error", "An error occurred while searching");
            return "errorPage";  // 예외 발생 시 보여줄 오류 페이지
        }
        return "board/search-result";
    }
}
