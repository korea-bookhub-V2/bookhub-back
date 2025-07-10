package com.example.bookhub_back.repository;

import com.example.bookhub_back.entity.PurchaseOrderApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderApprovalRepository extends JpaRepository<PurchaseOrderApproval, Long> {
}
