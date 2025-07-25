package com.example.bookhub_back.repository;

import com.example.bookhub_back.entity.EmployeeChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeChangeLogRepository extends JpaRepository<EmployeeChangeLog, Long> {
}
