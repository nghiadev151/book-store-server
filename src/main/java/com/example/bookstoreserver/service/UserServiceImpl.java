package com.example.bookstoreserver.service;

import com.example.bookstoreserver.model.User;
import com.example.bookstoreserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;
@Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

  @Override
  public List<User> getAllUser() {
    return userRepository.findAll();
  }

}
