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
<<<<<<< HEAD
        SignUp signUp = signUpRepository.findByUserid(username)
=======
        SignUp signUp = signUpRepository.findByUsername(username)
>>>>>>> 8a650a7a5806a17b08f4e3c2898fc74ec3888455
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return User.builder()
                .username(signUp.getUsername())
                .password(signUp.getUserpassword())
                .roles("USER") // 기본 역할 설정
                .build();
    }
<<<<<<< HEAD


=======
>>>>>>> 8a650a7a5806a17b08f4e3c2898fc74ec3888455
}
