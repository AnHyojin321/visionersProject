package com.example.visioners.dto;

<<<<<<< HEAD
import jakarta.persistence.*;

@Entity
@Table(name = "signup")
public class SignUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    @Column(nullable = false, unique = true)
    private String userid;
=======
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "signup") // 테이블 이름을 명시적으로 설정
public class SignUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
>>>>>>> 8a650a7a5806a17b08f4e3c2898fc74ec3888455

    @Column(nullable = false)
    private String userpassword;

    @Column(nullable = false)
<<<<<<< HEAD
    private String username;
=======
    private String email;
>>>>>>> 8a650a7a5806a17b08f4e3c2898fc74ec3888455

    @Column(nullable = false)
    private String phone;

<<<<<<< HEAD
    @Column(nullable = false)
    private String email;

=======
>>>>>>> 8a650a7a5806a17b08f4e3c2898fc74ec3888455
    // 기본 생성자
    public SignUp() {
    }

    // 모든 필드를 포함하는 생성자
<<<<<<< HEAD
    public SignUp(String userid, String userpassword,  String username, String phone, String email) {
        this.userid = userid;
        this.userpassword = userpassword;
        this.username = username;
        this.phone = phone;
        this.email = email;
    }

    // Getter와 Setter 메서드

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
=======
    public SignUp(String username, String userpassword, String email, String phone) {
        this.username = username;
        this.userpassword = userpassword;
        this.email = email;
        this.phone = phone;
    }

    // Getter와 Setter 메서드
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
>>>>>>> 8a650a7a5806a17b08f4e3c2898fc74ec3888455
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

<<<<<<< HEAD

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
=======
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
>>>>>>> 8a650a7a5806a17b08f4e3c2898fc74ec3888455
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
<<<<<<< HEAD

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
=======
}
>>>>>>> 8a650a7a5806a17b08f4e3c2898fc74ec3888455
