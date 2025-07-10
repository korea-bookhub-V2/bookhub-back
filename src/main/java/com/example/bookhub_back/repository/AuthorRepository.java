package com.example.bookhub_back.repository;

import com.example.bookhub_back.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
