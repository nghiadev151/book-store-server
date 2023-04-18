package com.example.bookstoreserver.controller;

import com.example.bookstoreserver.model.User;
import com.example.bookstoreserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UsersController {
    private final UserService userService;
@Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody User user){
        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUser(){
    List<User>user = userService.getAllUser();

        return ResponseEntity.ok(user);
    }

}
