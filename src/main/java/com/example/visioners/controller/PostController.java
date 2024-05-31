package com.example.visioners.controller;

import com.example.visioners.dto.Post;
import com.example.visioners.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/index")
    public String listPosts(Model model, @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Post> posts = postService.getPosts(pageable);
        model.addAttribute("posts", posts);
        return "board/index";
    }

    @GetMapping("/board")
    public String newPostForm() {
        return "board/board"; // 게시글 작성 폼 페이지 이름
    }

    @PostMapping("/board")
    public String savePost(@RequestParam String title,
                           @RequestParam String author,
                           @RequestParam String content,
                           @RequestParam String password) {
        LocalDate currentDate = LocalDate.now();
        Post post = new Post();
        post.setTitle(title);
        post.setAuthor(author);
        post.setContent(content);
        post.setPassword(password);
        post.setCalendar(currentDate);
        postService.savePost(post);
        return "redirect:/index";
    }

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model, Pageable pageable) {
        try {
            Optional<Post> post = postService.getPostById(id);
            if (post.isPresent()) {
                model.addAttribute("post", post.get());
                Post previousPost = postService.findPreviousPost(id);
                if (previousPost != null) {
                    model.addAttribute("previousPost", previousPost);
                } else {
                    System.out.println("이전 글이 없습니다.");
                }
                return "board/post-detail";
            } else {
                model.addAttribute("error", "해당 게시글을 찾을 수 없습니다.");
                return "errorPage";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "서버에서 오류가 발생했습니다.");
            return "errorPage";
        }
    }

    @PostMapping("/edit-post")
    public String editPost(@RequestParam Long postId,
                           @RequestParam String password,
                           Model model) {
        try {
            Optional<Post> optionalPost = postService.getPostById(postId);
            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();
                if (post.getPassword().equals(password)) {
                    model.addAttribute("post", post);
                    return "board/edit";
                } else {
                    model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
                    model.addAttribute("post", post);
                    return "board/post-detail";
                }
            } else {
                return "redirect:/index";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "서버에서 오류가 발생했습니다.");
            return "errorPage";
        }
    }

    @PostMapping("/update-post")
    public String updatePost(@RequestParam Long id,
                             @RequestParam(required = false) String title,
                             @RequestParam(required = false) String author,
                             @RequestParam(required = false) String content,
                             @RequestParam String password,
                             Model model) {
        try {
            Optional<Post> optionalPost = postService.getPostById(id);
            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();
                if (post.getPassword().equals(password)) {
                    if (title != null) post.setTitle(title);
                    if (author != null) post.setAuthor(author);
                    if (content != null) post.setContent(content);
                    postService.savePost(post);
                    return "redirect:/index";
                } else {
                    model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
                    model.addAttribute("post", post);
                    return "board/edit";
                }
            }
            return "edit";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "서버에서 오류가 발생했습니다.");
            return "errorPage";
        }
    }

    @GetMapping("/posts")
    public String getAllPosts(Model model, Pageable pageable) {
        model.addAttribute("posts", postService.getAllPosts(pageable));
        return "posts";
    }

    @PostMapping("/delete-post")
    public String deletePost(@RequestParam Long postId,
                             @RequestParam String password,
                             Model model) {
        try {
            Optional<Post> optionalPost = postService.getPostById(postId);
            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();
                if (post.getPassword().equals(password)) {
                    postService.deletePost(post);
                    return "redirect:/index";
                } else {
                    model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
                    model.addAttribute("post", post);
                    return "board/post-detail";
                }
            } else {
                return "redirect:/index";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "서버에서 오류가 발생했습니다.");
            return "errorPage";
        }
    }
}
