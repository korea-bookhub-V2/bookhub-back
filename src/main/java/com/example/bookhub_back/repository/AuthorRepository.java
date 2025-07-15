package com.example.bookhub_back.repository;

import com.example.bookhub_back.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByAuthorEmail(String authorEmail);

    Optional<Page<Author>> findByAuthorNameContaining(String authorName, Pageable pageable);
}
