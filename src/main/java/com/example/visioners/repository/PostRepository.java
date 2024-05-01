package com.example.visioners.repository;

import com.example.visioners.dto.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO visioners.post(title, author, content, password, calendar) " +
            "VALUES(:title, :author, :content, :password, :calendar)", nativeQuery = true)
    public void postIndex(@Param("title") String title,
                          @Param("author") String author,
                          @Param("content") String content,
                          @Param("password") String password,
                          @Param("calendar") Timestamp calendar);



    // 제목에 특정 키워드를 포함하는 게시물을 찾는 쿼리 메서드
    List<Post> findByTitleContaining(String keyword);

    // 작성자에 특정 키워드를 포함하는 게시물을 찾는 쿼리 메서드
    List<Post> findByAuthorContaining(String keyword);

}