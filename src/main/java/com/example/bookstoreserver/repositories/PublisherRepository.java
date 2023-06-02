package com.example.bookstoreserver.repositories;

import com.example.bookstoreserver.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Publisher findByName(String publisherName);
}
