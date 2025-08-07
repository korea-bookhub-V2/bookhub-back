package com.example.bookhub_back.controller.stock;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.PageResponseDto;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.stock.response.StockResponseDto;
import com.example.bookhub_back.service.stock.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.COMMON_API+"/stocks")
@RequiredArgsConstructor
@Tag(name = "Stock API", description = "재고 조회 API 입니다.")
public class StockController {

    private final StockService stockService;

    @Operation(summary = "재고 조회", description = "재고를 조회합니다")
    @GetMapping
    public ResponseEntity<ResponseDto<PageResponseDto<StockResponseDto>>> getStocks(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) String bookTitle){
        ResponseDto<PageResponseDto<StockResponseDto>> response = stockService.getFilteredStocks(page,size,branchId,bookTitle);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "재고 조회", description = "재고를 단건 조회합니다")
    @GetMapping("/{stockId}")
    public ResponseEntity<ResponseDto<StockResponseDto>> getStockById(

            @PathVariable Long stockId){
        ResponseDto<StockResponseDto> responseDto = stockService.getStockById(stockId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
