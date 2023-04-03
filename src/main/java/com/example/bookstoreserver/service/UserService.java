package com.example.bookstoreserver.service;

import com.example.bookstoreserver.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public User saveUser(User user);
    public List<User> getAllUser();
}
