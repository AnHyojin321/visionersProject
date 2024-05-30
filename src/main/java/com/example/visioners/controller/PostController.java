package com.example.visioners.controller;

import com.example.visioners.dto.Comment;
import com.example.visioners.repository.PostRepository;
import com.example.visioners.service.CommentService;
import com.example.visioners.service.PostService;
import com.example.visioners.dto.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository;

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
    public String getPostById(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            List<Comment> comments = commentService.getCommentsByPostId(id);
            model.addAttribute("post", post.get());
            model.addAttribute("comments", comments);
            return "board/post-detail";
        } else {
            model.addAttribute("error", "해당 게시글을 찾을 수 없습니다.");
            return "errorPage";  //아직 에러 페이지 만들진 않음
        }
    }


    @PostMapping("/edit-post")
    public String editPost(@RequestParam Long postId,
                           @RequestParam String password,
                           Model model) {
        // 데이터베이스에서  postId의 게시물 가져오기
        Optional<Post> optionalPost = postService.getPostById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            // 입력된 비밀번호와 저장된 비밀번호를 비교
            if (post.getPassword().equals(password)) {
                // 비밀번호가 일치하면 수정 페이지로 이동
                model.addAttribute("post", post);
                return "board/edit"; // 수정 페이지로 이동
            } else {
                // 비밀번호가 일치하지 않을 때 메시지를 추가하여 수정 페이지로 이동
                model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
                model.addAttribute("post", post); // 페이지 상태를 그대로 유지
                return "board/post-detail"; // 수정 페이지로 이동
            }
        } else {
            System.out.println("해당하는 게시물을 찾을 수 없습니다.");
        }

        return "redirect:/index";
    }



    @PostMapping("/update-post")
    public String updatePost(@RequestParam Long id,
                             @RequestParam(required = false) String title,
                             @RequestParam(required = false) String author,
                             @RequestParam(required = false) String content,
                             @RequestParam String password,
                             Model model) {
        Optional<Post> optionalPost = postService.getPostById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getPassword().equals(password)) {
                if (title != null) post.setTitle(title);
                if (author != null) post.setAuthor(author);
                if (content != null) post.setContent(content);
                // 작성일 변경 없이 저장
                postRepository.save(post);


                return "redirect:/index";
            } else {
                // 비밀번호가 일치하지 않을 때 메시지를 추가하여 수정 페이지로 이동
                model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
                model.addAttribute("post", post); // 페이지 상태를 그대로 유지
                return "board/edit"; // 수정 페이지로 이동
            }
        }
        return "edit";
    }


    @PostMapping("/delete-post")
    public String deletePost(@RequestParam Long postId,
                             @RequestParam String password,
                             Model model) {
        Optional<Post> optionalPost = postService.getPostById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getPassword().equals(password)) {
                postRepository.delete(post);

                return "redirect:/index";
            } else {
                // 비밀번호가 일치하지 않을 때 메시지를 추가하여 수정 페이지로 이동
                model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
                model.addAttribute("post", post); // 페이지 상태를 그대로 유지
                return "board/post-detail"; // 수정 페이지로 이동
            }
        } else {
            System.out.println("해당하는 게시물을 찾을 수 없습니다.");
            return "redirect:/index";
        }
    }






}
