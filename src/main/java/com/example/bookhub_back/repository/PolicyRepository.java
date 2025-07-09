package com.example.bookhub_back.repository;

import com.example.bookhub_back.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy,Long> {
}
