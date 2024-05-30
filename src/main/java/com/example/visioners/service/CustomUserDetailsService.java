package com.example.visioners.service;

import com.example.visioners.dto.SignUp;
import com.example.visioners.repository.SignUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SignUpRepository signUpRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SignUp signUp = signUpRepository.findByUserid(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return User.builder()
                .username(signUp.getUsername())
                .password(signUp.getUserpassword())
                .roles("USER") // 기본 역할 설정
                .build();
    }


}
