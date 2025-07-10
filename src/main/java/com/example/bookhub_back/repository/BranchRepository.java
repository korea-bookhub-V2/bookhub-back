package com.example.bookhub_back.repository;

import com.example.bookhub_back.entity.Branch;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    boolean existsByBranchName(String branchName);

    @Query("SELECT b FROM Branch b WHERE (:branchLocation IS NULL OR b.branchLocation LIKE CONCAT('%', :branchLocation, '%'))")
    List<Branch> searchBranch(@Param("branchLocation") String branchLocation);
}
