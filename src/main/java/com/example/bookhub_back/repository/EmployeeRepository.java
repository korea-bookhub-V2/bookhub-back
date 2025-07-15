package com.example.bookhub_back.repository;

import com.example.bookhub_back.common.constants.RegexConstants;
import com.example.bookhub_back.common.enums.Status;
import com.example.bookhub_back.entity.Employee;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmployeeNumber(Long employeeNumber);

    Optional<Employee> findByEmail(String email);

    @Query("""
            SELECT e FROM Employee e
            WHERE (:name IS NULL OR e.name LIKE CONCAT('%', :name, '%'))
            AND (:branchId IS NULL OR e.branchId.branchId = :branchId)
            AND (:positionId IS NULL OR e.positionId.positionId = :positionId)
            AND (:authorityId IS NULL OR e.authorityId.authorityId = :authorityId)
            AND (:status IS NULL OR e.status = :status)
        """)
    List<Employee> searchEmployee(
        @Param("name") String name,
        @Param("branchId") Long branchId,
        @Param("positionId") Long positionId,
        @Param("authorityId") Long authorityId,
        @Param("status") Status status);
}
