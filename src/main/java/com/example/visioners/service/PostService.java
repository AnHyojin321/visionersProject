package com.example.visioners.service;

import com.example.visioners.dto.Post;
import com.example.visioners.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void savePost(String title, String author, String content, String password, Timestamp calendar) {
        postRepository.postIndex(title, author, content, password, calendar);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> searchByTitle(String title) {
        return postRepository.findByTitleContaining(title);
    }

    public List<Post> searchByAuthor(String author) {
        return postRepository.findByAuthorContaining(author);
    }

    public void save(Post existingPost) {
        // 추가적인 저장 로직이 필요한 경우 구현
    }

    public List<Post> findByTitleContaining(String title) {
        return postRepository.findByTitleContaining(title) != null ? postRepository.findByTitleContaining(title) :
                new ArrayList<>();
    }

    public List<Post> findByAuthorContaining(String author) {
        return postRepository.findByAuthorContaining(author) != null ? postRepository.findByAuthorContaining(author) :
                new ArrayList<>();
    }

}
