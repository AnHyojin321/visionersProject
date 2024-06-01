package com.example.visioners.service;

import com.example.visioners.dto.User;
import com.example.visioners.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUserid(String userid) {
        Optional<User> userOptional = userRepository.findByUserid(userid);
        return userOptional.orElse(null);
    }
}
