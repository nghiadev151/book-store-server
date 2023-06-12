package com.example.bookstoreserver.controller;

import com.example.bookstoreserver.Validator;
import com.example.bookstoreserver.dto.cart.CartDto;
import com.example.bookstoreserver.dto.cart.CartRequest;
import com.example.bookstoreserver.exception.CustomExceptionHandler;
import com.example.bookstoreserver.exception.NotFoundException;
import com.example.bookstoreserver.exception.ResourceNotFoundException;
import com.example.bookstoreserver.model.Cart;
import com.example.bookstoreserver.model.CartItem;
import com.example.bookstoreserver.model.User;
import com.example.bookstoreserver.repositories.UserRepository;
import com.example.bookstoreserver.service.CartService;
import com.example.bookstoreserver.service.UserService;
import com.example.bookstoreserver.service.serviceImpl.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Autowired
    public JwtService jwtService;
   @Autowired
   public UserRepository userRepository;
   @Autowired
   private Validator validator;
   @Autowired
   private CustomExceptionHandler customExceptionHandler;
    @Autowired
    public AuthenticationManager authenticationManager;
    private final CartService cartService;

@Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public ResponseEntity<CartDto> getCartByUser(HttpServletRequest request){

        CartDto cartDto = this.cartService.getCartByUserId(request);
        if(cartDto==null){
            return null;
        }
               return ResponseEntity.ok(cartDto);
    }
@PostMapping()
public ResponseEntity<?> addToCart(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid CartRequest cartRequest, BindingResult bindingResult){
    if(bindingResult.hasErrors()){
        List<String> errorMessage = validator.getErrorMessage(bindingResult);
        return ResponseEntity.badRequest().body(errorMessage);
     }
    try {
        final String authHeader = request.getHeader("Authorization");
        final String token;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Authorization not valid");
        }
        token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("User not found"));

        Long userId = user.getId();
        Long productId = cartRequest.getProductId();
        int quantity = cartRequest.getQuantity();
        cartService.addToCart(userId,productId,quantity);
        return ResponseEntity.ok("Add to cart successfully");
    }catch (NotFoundException ex) {
        return customExceptionHandler.handleNotFoundException(ex);
    }

}
@PutMapping()
public ResponseEntity<?> updateQuantity(HttpServletRequest request, @RequestBody @Valid CartRequest cartRequest, BindingResult bindingResult){
    if(bindingResult.hasErrors()){
        List<String> errorMessage = validator.getErrorMessage(bindingResult);
        return ResponseEntity.badRequest().body(errorMessage);
    }
    try {
        final String authHeader = request.getHeader("Authorization");
        final String token;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Authorization not valid");
        }
        token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("User not found"));

        Long userId = user.getId();
        Long productId = cartRequest.getProductId();
        int quantity = cartRequest.getQuantity();
        cartService.updateQuantity(userId,productId,quantity);
        return ResponseEntity.ok("update quantity successfully");
    }catch (NotFoundException ex){
        return customExceptionHandler.handleNotFoundException(ex);
    }

}
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long cartItemId){
    try {
        cartService.removeFromCart(cartItemId);
        return ResponseEntity.ok().build();
    }catch (NotFoundException ex){
        return customExceptionHandler.handleNotFoundException(ex);
    }

    }
}
