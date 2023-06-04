package com.example.bookstoreserver.service.serviceImpl;

import com.example.bookstoreserver.exception.NotFoundException;
import com.example.bookstoreserver.model.Author;
import com.example.bookstoreserver.repositories.AuthorRepository;
import com.example.bookstoreserver.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    AuthorRepository authorRepository;
    @Override
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(Long id)
    {
        return authorRepository.findById(id).orElseThrow(()-> new NotFoundException("Author not found"));
    }
}
