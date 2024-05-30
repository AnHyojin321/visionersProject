package com.example.visioners.repository;

import com.example.visioners.dto.SignUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface SignUpRepository extends JpaRepository<SignUp, Long> {
    Optional<SignUp> findByUserid(String userid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO visioners.signup (userid, userpassword, username, phone, email) " +
            "VALUES(:userid, :userpassword, :username, :phone, :email)", nativeQuery = true)
    void signup(@Param("userid") String userid,
                @Param("userpassword") String userpassword,
                @Param("username") String username,
                @Param("phone") String phone,
                @Param("email") String email);
}