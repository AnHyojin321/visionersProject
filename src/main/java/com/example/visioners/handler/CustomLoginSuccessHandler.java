package com.example.visioners.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 사용자명과 비밀번호를 검사
        String userid = authentication.getName();
        String userpassword = request.getParameter("password");

        if ("admin".equals(userid) && "admin".equals(userpassword)) {
            // 관리자 계정인 경우 admin 페이지로 리디렉션
            setDefaultTargetUrl("/admin");
        } else {
            // 일반 사용자 계정인 경우 메인 페이지로 리디렉션
            setDefaultTargetUrl("/main");
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
