package com.example.bookhub_back.repository.stock;

import com.example.bookhub_back.entity.StockLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockLogRepository extends JpaRepository<StockLog,Long> {
}
