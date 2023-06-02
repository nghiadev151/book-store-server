package com.example.bookstoreserver.service;

import com.example.bookstoreserver.model.Publisher;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PublisherService {
    public List<Publisher> getAllPublisher();
    public Publisher getPublisherById(Long id);
}
