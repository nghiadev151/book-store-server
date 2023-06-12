package com.example.bookstoreserver.service;

import com.example.bookstoreserver.dto.order.OrderRequest;
import com.example.bookstoreserver.model.Order;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    public void saveOrder(HttpServletRequest request, OrderRequest orderRequest);
    public Order getOrderById(Long id);

    public Page<Order> getAllOrder(Pageable pageable);
    public void updateOrder(Long id, OrderRequest orderRequest);
    public void deleteOrder(Long id);
}
