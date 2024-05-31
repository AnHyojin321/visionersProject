package com.example.visioners.repository;

import com.example.visioners.dto.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.id < :id ORDER BY p.id DESC")
    List<Post> findPreviousPosts(Long id);

    Page<Post> findByTitleContaining(String title, Pageable pageable);

    Page<Post> findByAuthorContaining(String author, Pageable pageable);

    Page<Post> findAll(Pageable pageable);
}
