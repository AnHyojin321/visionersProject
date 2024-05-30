package com.example.visioners.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "signup")
public class SignUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    @Column(nullable = false, unique = true)
    private String userid;

    @Column(nullable = false)
    private String userpassword;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    // 기본 생성자
    public SignUp() {
    }

    // 모든 필드를 포함하는 생성자
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
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}