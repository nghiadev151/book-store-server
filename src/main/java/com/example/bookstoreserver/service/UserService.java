package com.example.bookstoreserver.service;

import com.example.bookstoreserver.dto.user.UserDto;
import com.example.bookstoreserver.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public User saveUser(User user);
    public List<UserDto> getAllUser();
    public UserDto getUserById(Long id);
    public void updateUser(Long userId, UserDto updateUser);
    public void deleteUser(Long id);
    public UserDto getUserByToken(HttpServletRequest request);




}
