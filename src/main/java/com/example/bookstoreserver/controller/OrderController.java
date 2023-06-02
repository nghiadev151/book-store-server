package com.example.bookstoreserver.controller;

import com.example.bookstoreserver.dto.order.OrderRequest;
import com.example.bookstoreserver.model.Order;
import com.example.bookstoreserver.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    public OrderService orderService;
    @PostMapping("/checkout")
    public ResponseEntity<?> checkOut(HttpServletRequest request, @RequestBody OrderRequest orderRequest){
        orderService.saveOrder(request, orderRequest);
        return ResponseEntity.ok("Checkout successfully");

    }
    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrder(HttpServletRequest request){

        return ResponseEntity.ok(orderService.getAllOrder(request));
    }
}
