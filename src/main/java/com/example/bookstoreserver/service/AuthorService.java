package com.example.bookstoreserver.service;

import com.example.bookstoreserver.model.Author;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AuthorService {
    public List<Author> getAllAuthor();
    public Author getAuthorById(Long id);
}
