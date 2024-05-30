package com.example.visioners.service;

import com.example.visioners.dto.SignUp;
import com.example.visioners.repository.SignUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class SignUpService {
    private static final Logger logger = LoggerFactory.getLogger(SignUpService.class);

    @Autowired
    private SignUpRepository signUpRepository;

<<<<<<< HEAD
    public boolean checkIfUserExists(String userid) {
        return signUpRepository.findByUserid(userid).isPresent();
    }

    public SignUpService(SignUpRepository signUpRepository) {
        this.signUpRepository = signUpRepository;
    }


    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveSignUp(String userid, String password, String username, String phone, String email) {
        String encodedPassword = passwordEncoder.encode(password); // 비밀번호 암호화
        SignUp signUp = new SignUp(userid, encodedPassword, username, phone, email);
=======
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveSignUp(String username, String password, String email, String phone) {
        String encodedPassword = passwordEncoder.encode(password); // 비밀번호 암호화
        SignUp signUp = new SignUp(username, encodedPassword, email, phone);
>>>>>>> 8a650a7a5806a17b08f4e3c2898fc74ec3888455
        signUpRepository.save(signUp);
    }

    public String authenticate(String username, String password) {
        logger.info("Authenticating user: {}", username);
<<<<<<< HEAD
        Optional<SignUp> userOpt = signUpRepository.findByUserid(username);
=======
        Optional<SignUp> userOpt = signUpRepository.findByUsername(username);
>>>>>>> 8a650a7a5806a17b08f4e3c2898fc74ec3888455
        if (userOpt.isPresent()) {
            SignUp user = userOpt.get();
            if (!passwordEncoder.matches(password, user.getUserpassword())) { // 암호화된 비밀번호와 비교
                logger.info("Password mismatch for user: {}", username);
                return "비밀번호를 다시 입력하세요";
            }
            logger.info("Authentication successful for user: {}", username);
            return "SUCCESS";
        }
        logger.info("User not found: {}", username);
        return "아이디를 다시 입력하세요";
    }
}
