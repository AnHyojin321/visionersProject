package com.example.visioners.service;

import com.example.visioners.dto.Post;
import com.example.visioners.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void savePost(String title, String author, String content, Timestamp calendar) {
        postRepository.postIndex(title, author, content, calendar);
    }

    public Page<Post> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
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