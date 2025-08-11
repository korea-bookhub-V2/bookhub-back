package com.example.bookhub_back.repository;

import com.example.bookhub_back.entity.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher,Long> {
    @Query("""
SELECT p
FROM Publisher p
WHERE(:keyword IS NULL OR p.publisherName LIKE CONCAT('%', :keyword, '%'))
ORDER BY p.publisherId DESC
""")
    Page<Publisher> findByPublisherNameContaining(String keyword, Pageable pageable);
}
