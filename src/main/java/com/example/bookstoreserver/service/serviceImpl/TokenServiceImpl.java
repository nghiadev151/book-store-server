package com.example.bookstoreserver.service.serviceImpl;

import com.example.bookstoreserver.config.UserInfoUserDetailsService;
import com.example.bookstoreserver.dto.user.AuthenticationResponse;
import com.example.bookstoreserver.dto.user.LoginRequest;
import com.example.bookstoreserver.dto.user.RegisterRequest;
import com.example.bookstoreserver.exception.NotFoundException;
import com.example.bookstoreserver.exception.UserException;
import com.example.bookstoreserver.model.Token;
import com.example.bookstoreserver.model.User;
import com.example.bookstoreserver.repositories.TokenRepository;
import com.example.bookstoreserver.repositories.UserRepository;
import com.example.bookstoreserver.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserInfoUserDetailsService userInfoUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public void saveRefreshToken(User user, String refreshToken) {
        Token token = new Token();
        token.setUser(user);
        token.setRefreshToken(refreshToken);
        tokenRepository.save(token);
    }

    @Override
    public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String username;
        AuthenticationResponse authenticationResponse = null;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return null;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if(username != null){
            UserDetails userDetails = userInfoUserDetailsService.loadUserByUsername(username);
            Token token = this.tokenRepository.findByRefreshToken(refreshToken);
            if(jwtService.validateToken(refreshToken, userDetails)&& token != null){
                this.tokenRepository.delete(token);
                String newRefreshToken = jwtService.generateRefreshToken(username);
                User user = userRepository.findByUsername(username).orElseThrow();
                this.saveRefreshToken(user, newRefreshToken);
                String newToken = jwtService.generateToken(username);
                authenticationResponse = new AuthenticationResponse();
                authenticationResponse.setAccessToken(newToken);
                authenticationResponse.setRefreshToken(newRefreshToken);
                
            }
        }
        return authenticationResponse;
    }


    @Override
    public AuthenticationResponse authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(loginRequest.getUsername());
            String refreshToken = jwtService.generateRefreshToken(loginRequest.getUsername());
            this.saveRefreshToken(user, refreshToken);
            AuthenticationResponse auth = new AuthenticationResponse();
            auth.setAccessToken(token);
            auth.setRefreshToken(refreshToken);
           return auth;
        } else {
            throw new UserException("invalid user request!");
        }
    }
}
