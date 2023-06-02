package com.example.bookstoreserver.service;

import com.example.bookstoreserver.dto.order.OrderRequest;
import com.example.bookstoreserver.model.Order;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    public void saveOrder(HttpServletRequest request, OrderRequest orderRequest);
    public Order getOrderById(Long id);
    public List<Order> getAllOrder(HttpServletRequest request);
}
