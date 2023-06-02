package com.example.bookstoreserver.controller;

import com.example.bookstoreserver.dto.user.UserDto;
import com.example.bookstoreserver.model.User;
import com.example.bookstoreserver.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/user")
public class UsersController {
    private final UserService userService;
@Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody User user){
        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }
    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUser(){
    List<UserDto>user = userService.getAllUser();
        return ResponseEntity.ok(user);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        userService.getUserById(id);
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @GetMapping("/info")
    public ResponseEntity<?> getUserByToken (HttpServletRequest request){
                return ResponseEntity.ok(userService.getUserByToken(request));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto updateUser){
        userService.updateUser(id, updateUser);
        return ResponseEntity.ok("Update user successfully");
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
    userService.deleteUser(id);
    return ResponseEntity.ok("Delete successfuly");
    }

}
