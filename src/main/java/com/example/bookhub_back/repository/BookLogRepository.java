package com.example.bookhub_back.repository;

import com.example.bookhub_back.entity.BookLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookLogRepository extends JpaRepository <BookLog, Long>{
    List<BookLog> findByBookIsbn_Isbn(String isbn);
}
