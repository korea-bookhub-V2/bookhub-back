package com.example.bookhub_back.repository.customer;

import com.example.bookhub_back.entity.CustomerOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderDetailRepository extends JpaRepository<CustomerOrderDetail,Long> {
}
