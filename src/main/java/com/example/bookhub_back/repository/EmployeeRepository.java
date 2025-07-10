package com.example.bookhub_back.repository;

import com.example.bookhub_back.common.constants.RegexConstants;
import com.example.bookhub_back.entity.Employee;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmployeeNumber(Long employeeNumber);

    Optional<Employee> findByEmail(String email);
}
