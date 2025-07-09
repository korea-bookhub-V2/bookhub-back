package com.example.bookhub_back.repository;

import com.example.bookhub_back.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
