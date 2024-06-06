package com.example.visioners.controller;

import com.example.visioners.dto.Comment;
import com.example.visioners.dto.SignUp;
import com.example.visioners.repository.PostRepository;
import com.example.visioners.repository.SignUpRepository;
import com.example.visioners.service.CommentService;
import com.example.visioners.service.PostService;
import com.example.visioners.dto.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.time.LocalDate;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SignUpRepository signUpRepository;

    @GetMapping("/index")
    public String listPosts(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<Post> posts = postService.getPosts(pageable);
        model.addAttribute("posts", posts);
        return "board/index";
    }

    @RequestMapping("/index.html")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/board")
    public String newPostForm() {
        return "board/board"; // 게시글 작성 폼 페이지 이름
    }


    @PostMapping("/board")
    public String savePost(@RequestParam String title,
                           @RequestParam String content) {
        LocalDate currentDate = LocalDate.now();

        Post post = new Post();

        post.setTitle(title);
        post.setContent(content);
        post.setCalendar(currentDate);

        // 현재 로그인한 사용자의 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 로그인한 사용자의 username

        // 게시글 작성자로 username 설정
        post.setAuthor(username);

        // 데이터베이스에 게시글 저장
        postRepository.save(post);

        // 데이터베이스에 저장
        postRepository.save(post);

        // 저장 후에 다시 게시글 목록 페이지로 리다이렉트
        return "redirect:/index";
    }


    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }
    /*
        @GetMapping("/index")
        public String showAllPosts(Model model) {
            List<Post> posts = postService.getAllPosts();
            model.addAttribute("posts", posts);
            return "board/index";
        }
    */
    @GetMapping("/posts/{id}")
    public String getPostById(@PathVariable Long id, Model model, Principal principal) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            List<Comment> comments = commentService.getCommentsByPostId(id);
            model.addAttribute("post", post.get());
            model.addAttribute("comments", comments);
            model.addAttribute("currentUserName", principal.getName()); // 현재 로그인한 사용자 이름 추가
            return "board/post-detail";
        } else {
            model.addAttribute("error", "해당 게시글을 찾을 수 없습니다.");
            return "errorPage"; // 아직 에러 페이지 만들지 않음
        }
    }


    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


    @GetMapping("/edit-post/{postId}")  // 수정 페이지는 GET 요청을 사용해야 합니다.
    public String editPost(@PathVariable Long postId, Model model) {
        String currentUserName = getCurrentUserName();
        return postService.getPostById(postId)
                .map(post -> {
                    if (post.getAuthor().equals(currentUserName)) {
                        model.addAttribute("post", post);
                        return "board/edit";
                    } else {
                        model.addAttribute("errorMessage", "수정 권한이 없습니다.");
                        return "board/post-detail";
                    }
                }).orElse("redirect:/index");
    }

    @PostMapping("/update-post/{id}")
    public String updatePost(@PathVariable Long id,
                             @RequestParam(required = false) String title,
                             @RequestParam(required = false) String content,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        String currentUserName = getCurrentUserName();
        return postService.getPostById(id)
                .map(post -> {
                    if (post.getAuthor().equals(currentUserName)) {
                        post.setTitle(title);
                        post.setContent(content);
                        postRepository.save(post);
                        redirectAttributes.addFlashAttribute("message", "게시글이 수정되었습니다");
                        return "redirect:/index";
                    } else {
                        model.addAttribute("errorMessage", "수정 권한이 없습니다.");
                        return "board/edit";
                    }
                }).orElse("redirect:/index");
    }

    @PostMapping("/delete-post/{postId}")
    public String deletePost(@PathVariable Long postId,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        String currentUserName = getCurrentUserName();
        return postService.getPostById(postId)
                .map(post -> {
                    if (post.getAuthor().equals(currentUserName)) {
                        postRepository.delete(post);
                        redirectAttributes.addFlashAttribute("message", "게시글이 삭제되었습니다");
                        return "redirect:/index";
                    } else {
                        model.addAttribute("errorMessage", "삭제 권한이 없습니다.");
                        return "board/post-detail";
                    }
                }).orElse("redirect:/index");
    }


}