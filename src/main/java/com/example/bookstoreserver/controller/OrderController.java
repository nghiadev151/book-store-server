package com.example.bookstoreserver.controller;

import com.example.bookstoreserver.Validator;
import com.example.bookstoreserver.dto.order.OrderRequest;
import com.example.bookstoreserver.exception.CustomExceptionHandler;
import com.example.bookstoreserver.exception.NotFoundException;
import com.example.bookstoreserver.exception.OrderException;
import com.example.bookstoreserver.model.Order;
import com.example.bookstoreserver.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    public OrderService orderService;
    @Autowired
    private Validator validator;
    @Autowired
    private CustomExceptionHandler customExceptionHandler;
    @PostMapping("/checkout")
    public ResponseEntity<?> checkOut(HttpServletRequest request, @RequestBody @Valid OrderRequest orderRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errorMessage = validator.getErrorMessage(bindingResult);
            return ResponseEntity.badRequest().body(errorMessage);
         }
        try {
            orderService.saveOrder(request, orderRequest);

            return ResponseEntity.ok("Checkout successfully");
        }catch (OrderException ex){
            return customExceptionHandler.handleOrderException(ex);
        }catch (NotFoundException ex){
            return customExceptionHandler.handleNotFoundException(ex);
        }


    }
    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(orderService.getOrderById(id));
        }catch (NotFoundException ex){
            return customExceptionHandler.handleNotFoundException(ex);
        }

    }
    @GetMapping("/orders")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllOrder(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size){
        try {
            Pageable pageable = PageRequest.of(page,size);
            Page<Order> order = orderService.getAllOrder(pageable);
            return ResponseEntity.ok(order);
        }catch (NotFoundException ex){
            return customExceptionHandler.handleNotFoundException(ex);
        }

    }
    @PutMapping("/order/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody @Valid OrderRequest orderRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errorMessage = validator.getErrorMessage(bindingResult);
            return ResponseEntity.badRequest().body(errorMessage);
        }
        try {
            orderService.updateOrder(id, orderRequest);
            return ResponseEntity.ok("Update success!");
        }catch (NotFoundException ex){
            return customExceptionHandler.handleNotFoundException(ex);
        }

    }
    @DeleteMapping("/order/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id){
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Delete success!");
        }catch (NotFoundException ex){
            return customExceptionHandler.handleNotFoundException(ex);
        }

    }
}
