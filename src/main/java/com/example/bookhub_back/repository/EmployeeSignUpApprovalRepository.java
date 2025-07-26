package com.example.bookhub_back.repository;

import com.example.bookhub_back.common.enums.IsApproved;
import com.example.bookhub_back.entity.Employee;
import com.example.bookhub_back.entity.EmployeeSignUpApproval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeSignUpApprovalRepository extends JpaRepository<EmployeeSignUpApproval, Long> {
    Optional<EmployeeSignUpApproval> findByEmployeeIdAndIsApproved(Employee employee, IsApproved isApproved);

    @Query("""
            SELECT e FROM EmployeeSignUpApproval e
            WHERE e.isApproved = :isApproved
        """)
    Page<EmployeeSignUpApproval> searchEmployee(IsApproved isApproved, Pageable pageable);
}