package com.example.visioners.service;

import com.example.visioners.dto.Post;
import com.example.visioners.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    public void savePost(Post post) {
        post.setCreatedDate(LocalDateTime.now());
        postRepository.save(post);
    }

    public Page<Post> getPosts(Pageable pageable) {
        logger.debug("PostService: getPosts with pageable = {}", pageable);
        return postRepository.findAll(pageable);
    }

    public Optional<Post> getPostById(Long id) {
        logger.debug("PostService: getPostById with id = {}", id);
        return postRepository.findById(id);
    }

    public Post findPreviousPost(Long id) {
        logger.debug("PostService: findPreviousPost with id = {}", id);
        return postRepository.findPreviousPosts(id).stream().findFirst().orElse(null);
    }

    public void deletePost(Post post) {
        logger.debug("PostService: deletePost with post = {}", post);
        postRepository.delete(post);
    }

    public Page<Post> findByTitleContaining(String title, Pageable pageable) {
        logger.debug("PostService: findByTitleContaining with title = {}, pageable = {}", title, pageable);
        return postRepository.findByTitleContaining(title, pageable);
    }

    public Page<Post> findByAuthorContaining(String author, Pageable pageable) {
        logger.debug("PostService: findByAuthorContaining with author = {}, pageable = {}", author, pageable);
        return postRepository.findByAuthorContaining(author, pageable);
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        logger.debug("PostService: getAllPosts with pageable = {}", pageable);
        return postRepository.findAll(pageable);
    }
}
