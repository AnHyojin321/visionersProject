package com.example.visioners.controller;

import com.example.visioners.repository.PostRepository;
import com.example.visioners.service.PostService;
import com.example.visioners.dto.Post;
import org.springframework.beans.factory.annotation.Autowired;
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

        // 폼 데이터로 받은 정보를 이용하여 Post 객체 생성
        Post post = new Post();

        post.setTitle(title);
        post.setAuthor(author);
        post.setContent(content);
        post.setPassword(password);
        post.setCalendar(currentDate); // 현재 날짜 설정

        // 데이터베이스에 저장
        postRepository.save(post);

        // 저장 후에는 다시 홈 페이지로 리다이렉트
        return "redirect:/index";
    }




    private final PostService postService; // 생성자 주입을 위한 필드 선언

    // 생성자 정의
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/index")
    public String showAllPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "board/index"; // Assuming your view name is "index.html"
    }

    @GetMapping("/posts/{id}")
    public String getPostById(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());  // Optional에서 Post 객체를 꺼내어 모델에 추가
            return "board/post-detail";
        } else {
            model.addAttribute("error", "해당 게시글을 찾을 수 없습니다.");
            return "errorPage";  // 적절한 오류 페이지나 메시지를 표시할 뷰를 반환
        }
    }


    @PostMapping("/edit-post")
    public String editPost(@RequestParam Long postId,
                           @RequestParam String password,
                           Model model) {
        // 데이터베이스에서 해당 postId의 게시물을 가져옵니다.
        Optional<Post> optionalPost = postService.getPostById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            // 입력된 비밀번호와 저장된 비밀번호를 비교합니다.
            if (post.getPassword().equals(password)) {
                // 비밀번호가 일치하면 수정 페이지로 이동합니다.
                model.addAttribute("post", post);
                return "board/edit"; // 수정 페이지로 이동하는 뷰 이름
            } else {
                // 비밀번호가 일치하지 않으면 에러 메시지를 표시하거나 다시 입력 폼을 보여줄 수 있습니다.
                // 여기서는 임시로 에러 메시지를 출력합니다.
                System.out.println("비밀번호가 일치하지 않습니다.");
            }
        } else {
            // 게시물을 찾을 수 없는 경우 에러 메시지를 출력하거나 다른 처리를 할 수 있습니다.
            // 여기서는 임시로 에러 메시지를 출력합니다.
            System.out.println("해당하는 게시물을 찾을 수 없습니다.");
        }

        // 비밀번호가 일치하지 않거나 게시물을 찾을 수 없는 경우 게시물 목록 페이지로 리다이렉트합니다.
        return "redirect:/index";
    }



    @PostMapping("/update-post")
    public String updatePost(@RequestParam Long id,
                             @RequestParam String title,
                             @RequestParam String author,
                             @RequestParam String content,
                             @RequestParam String password,
                             RedirectAttributes redirectAttributes) {
        Optional<Post> optionalPost = postService.getPostById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getPassword().equals(password)) {
                post.setTitle(title);
                post.setAuthor(author);
                post.setContent(content);
                // 작성일 변경 없이 저장
                postRepository.save(post);

                redirectAttributes.addFlashAttribute("message", "게시글이 수정되었습니다.");
                return "redirect:/index";
            } else {
                System.out.println("비밀번호가 일치하지 않습니다.");
            }
        } else {
            System.out.println("해당하는 게시물을 찾을 수 없습니다.");
        }

        return "edit";
    }

    @PostMapping("/delete-post")
    public String deletePost(@RequestParam Long postId,
                             @RequestParam String password,
                             RedirectAttributes redirectAttributes) {
        // 데이터베이스에서 해당 postId의 게시물을 가져옵니다.
        Optional<Post> optionalPost = postService.getPostById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            // 입력된 비밀번호와 저장된 비밀번호를 비교합니다.
            if (post.getPassword().equals(password)) {
                // 비밀번호가 일치하면 게시글을 삭제합니다.
                postRepository.delete(post);
                // 삭제 후에는 삭제되었음을 사용자에게 알리고, 게시글 목록 페이지로 리다이렉트합니다.
                redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 삭제되었습니다.");
                return "redirect:/index";
            } else {
                // 비밀번호가 일치하지 않으면 에러 메시지를 표시합니다.
                redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
            }
        } else {
            // 게시물을 찾을 수 없는 경우 에러 메시지를 표시합니다.
            redirectAttributes.addFlashAttribute("error", "해당하는 게시물을 찾을 수 없습니다.");
        }

        // 비밀번호가 일치하지 않거나 게시물을 찾을 수 없는 경우 삭제 페이지로 리다이렉트합니다.
        return "redirect:/delete-page";
    }





}
