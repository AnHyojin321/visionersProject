package com.example.visioners.controller;

import com.example.visioners.dto.Comment;
import com.example.visioners.dto.Post;
import com.example.visioners.repository.PostRepository;
import com.example.visioners.service.CommentService;
import com.example.visioners.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    private PostRepository postRepository;


    @Autowired
    public AdminController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/adminLogin")
    public String adminLoginPage() {
        return "admin/adminLogin"; // 타임리프 템플릿으로 이동
    }

    @PostMapping("/verifyAdmin")
    public String verifyAdmin(@RequestParam String authCode) {
        String correctAuthCode = "123456"; // 실제로는 안전한 방식으로 관리되어야 함

        if (authCode.equals(correctAuthCode)) {
            // 사용자에게 ADMIN 권한 부여
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken("admin", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
            SecurityContextHolder.getContext().setAuthentication(auth);

            return "redirect:/admin"; // 인증 성공 시 관리자 페이지로 리디렉션
        } else {
            return "redirect:/adminLogin?error=true"; // 인증 실패 시 다시 인증 페이지로 리디렉션
        }
    }

    @GetMapping("/admin")
    public String adminPage(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<Post> posts = postService.getPosts(pageable);
        model.addAttribute("posts", posts);
        return "admin/admin"; // 타임리프 템플릿 경로 수정
    }

    @GetMapping("/admin/posts/{id}")
    public String adminPostDetail(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            List<Comment> comments = commentService.getCommentsByPostId(id);
            model.addAttribute("post", post.get());
            model.addAttribute("comments", comments);
            return "admin/adminPostDetail"; // 타임리프 템플릿 경로 수정
        } else {
            return "redirect:/admin?error=postNotFound";
        }
    }

    @PostMapping("/admin/posts/{id}/comments")
    public String addComment(@PathVariable Long id,
                             @RequestParam String commentContent) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            Comment comment = new Comment(commentContent, post.get());
            commentService.addComment(comment);
            return "redirect:/admin/posts/" + id;
        } else {
            return "redirect:/admin?error=postNotFound";
        }
    }

    @PostMapping("/admin/posts/{postId}/comments/{commentId}")
    public String updateComment(@PathVariable Long postId,
                                @PathVariable Long commentId,
                                @RequestParam String commentContent) {
        Optional<Comment> comment = commentService.getCommentById(commentId);
        if (comment.isPresent()) {
            Comment existingComment = comment.get();
            existingComment.setComment(commentContent);
            commentService.updateComment(existingComment);
            return "redirect:/admin/posts/" + postId;
        } else {
            return "redirect:/admin?error=commentNotFound";
        }
    }

    @PostMapping("/admin/deletePosts")
    public String deleteSelectedPosts(@RequestParam List<Long> selectedIds) {
        for (Long id : selectedIds) {
            postRepository.deleteById(id);
        }
        return "redirect:/admin";
    }

}
