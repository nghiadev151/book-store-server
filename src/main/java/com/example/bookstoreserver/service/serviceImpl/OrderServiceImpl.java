package com.example.bookstoreserver.service.serviceImpl;

import com.example.bookstoreserver.dto.order.OrderRequest;
import com.example.bookstoreserver.exception.NotFoundException;
import com.example.bookstoreserver.model.*;
import com.example.bookstoreserver.repositories.OrderDetailRepository;
import com.example.bookstoreserver.repositories.OrderRepository;
import com.example.bookstoreserver.repositories.ProductRepository;
import com.example.bookstoreserver.repositories.UserRepository;
import com.example.bookstoreserver.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    public JwtService jwtService;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public OrderRepository orderRepository;
    @Autowired
    public OrderDetailRepository orderDetailRepository;
    @Autowired
    public ProductRepository productRepository;
    @Override
    public void saveOrder(HttpServletRequest request, OrderRequest orderRequest) {
        final String authHeader = request.getHeader("Authorization");
        final String token;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("User not exists"));
        Cart cart = user.getCart();
        Order order = new Order();
        order.setAddress(orderRequest.getAddress());
        order.setPhone(orderRequest.getPhone());
        order.setTotalPrice(orderRequest.getTotalPrice());
        order.setUser(user);
        orderRepository.save(order);
        if(cart.getCartItems() != null){

            for (CartItem od: cart.getCartItems()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setQuantity(od.getQuantity());
                orderDetail.setUnitPrice(od.getQuantity()*od.getProduct().getPrice());
                orderDetail.setOrder(order);
                orderDetail.setProduct(od.getProduct());
                Product product = od.getProduct();
                product.setSales(product.getSales() + od.getQuantity());
                productRepository.save(product);
                orderDetailRepository.save(orderDetail);
            }
        }
    }

    @Override
    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(()-> new NotFoundException("Order not found"));

        return order;
    }

    @Override
    public List<Order> getAllOrder(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String token;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return null;
        }
        token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("User not found"));
        List<Order> orders = orderRepository.findByUserId(user.getId());
        return orders;
    }
}
