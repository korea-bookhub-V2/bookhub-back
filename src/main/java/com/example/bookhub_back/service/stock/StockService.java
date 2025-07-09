package com.example.bookhub_back.service.stock;

import com.example.bookhub_back.dto.PageResponseDto;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.stock.request.StockUpdateRequestDto;
import com.example.bookhub_back.dto.stock.response.StockResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

public interface StockService {
    ResponseDto<Void> updateStock(Long stockId, @Valid StockUpdateRequestDto dto);

    ResponseDto<PageResponseDto<StockResponseDto>> getFilteredStocks(@Min(0) int page, @Min(1) int size, Long branchId, String bookTitle);
}
