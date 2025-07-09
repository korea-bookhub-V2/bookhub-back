package com.example.bookhub_back.repository.stock;

import com.example.bookhub_back.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,Long> {
}
