package com.example.bookstoreserver.service;

import com.example.bookstoreserver.dto.user.AuthenticationResponse;
import com.example.bookstoreserver.dto.user.LoginRequest;
import com.example.bookstoreserver.dto.user.RegisterRequest;
import com.example.bookstoreserver.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface TokenService {
    public  void saveRefreshToken(User user, String refreshToken);

    public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
    public AuthenticationResponse authenticate(LoginRequest loginRequest);
}
