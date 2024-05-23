package com.example.visioners.repository;

import com.example.visioners.dto.SignUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface SignUpRepository extends JpaRepository<SignUp, Long> {
    Optional<SignUp> findByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO visioners.signup (username, userpassword, email, phone) " +
            "VALUES(:username, :userpassword, :email, :phone)", nativeQuery = true)
    void signup(@Param("username") String username,
                @Param("userpassword") String userpassword,
                @Param("email") String email,
                @Param("phone") String phone);
}
