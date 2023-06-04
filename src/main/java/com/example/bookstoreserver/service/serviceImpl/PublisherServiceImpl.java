package com.example.bookstoreserver.service.serviceImpl;

import com.example.bookstoreserver.exception.NotFoundException;
import com.example.bookstoreserver.model.Publisher;
import com.example.bookstoreserver.repositories.PublisherRepository;
import com.example.bookstoreserver.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PublisherServiceImpl implements PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;
    @Override
    public List<Publisher> getAllPublisher() {
        return publisherRepository.findAll();
    }

    @Override
    public Publisher getPublisherById(Long id) {
        return publisherRepository.findById(id).orElseThrow(()-> new NotFoundException("Publisher not found"));
    }
}
