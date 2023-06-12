package com.example.bookstoreserver.controller;

import com.example.bookstoreserver.Validator;
import com.example.bookstoreserver.dto.user.UserDto;
import com.example.bookstoreserver.exception.CustomExceptionHandler;
import com.example.bookstoreserver.exception.UserException;
import com.example.bookstoreserver.model.User;
import com.example.bookstoreserver.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UsersController {
    @Autowired
    private Validator validator;
    @Autowired
    private CustomExceptionHandler customExceptionHandler;
    private final UserService userService;
@Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String>errorMessage = validator.getErrorMessage(bindingResult);
            return ResponseEntity.badRequest().body(errorMessage);
        }
        try {

            userService.saveUser(user);
            return ResponseEntity.ok(user);
        }catch (UserException ex){
            return customExceptionHandler.handleUserException(ex);
        }
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
