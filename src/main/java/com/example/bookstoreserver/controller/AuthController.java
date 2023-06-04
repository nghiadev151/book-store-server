package com.example.bookstoreserver.controller;

import com.example.bookstoreserver.config.UserInfoUserDetailsService;
import com.example.bookstoreserver.dto.user.AuthenticationResponse;
import com.example.bookstoreserver.dto.user.LoginRequest;
import com.example.bookstoreserver.dto.user.RegisterRequest;
import com.example.bookstoreserver.model.Token;
import com.example.bookstoreserver.model.User;
import com.example.bookstoreserver.repositories.TokenRepository;
import com.example.bookstoreserver.repositories.UserRepository;
import com.example.bookstoreserver.service.TokenService;
import com.example.bookstoreserver.service.serviceImpl.JwtService;
import com.example.bookstoreserver.service.serviceImpl.TokenServiceImpl;
import com.example.bookstoreserver.service.serviceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
private UserInfoUserDetailsService userInfoUserDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
//         Kiểm tra xem username đã tồn tại hay chưa
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }
        // Lưu người dùng mới vào cơ sở dữ liệu
        userService.saveUser(user);

        // Trả về thông báo thành công
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateAndGetToken(@RequestBody LoginRequest loginRequest) {
       return ResponseEntity.ok(tokenService.authenticate(loginRequest)) ;
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest  request){
        final String authHeader = request.getHeader("Authorization");
        final String token;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Authorization not valid");
        }
        token = authHeader.substring(7);
        if (jwtService.isTokenExpired(token)) {
            return ResponseEntity.badRequest().body("Token has already expired.");
        }
        // Thêm token vào danh sách token đã hết hạn
        jwtService.addToExpiredTokenList(token);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout successful.");
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(tokenService.refreshToken(request, response));
    }
}
