package com.example.bookhub_back.controller.stock;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.common.enums.StockActionType;
import com.example.bookhub_back.dto.PageResponseDto;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.stock.response.StockLogResponseDto;
import com.example.bookhub_back.service.stock.StockLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(ApiMappingPattern.ADMIN_API+"/stock-logs")
@RequiredArgsConstructor
@Tag(name = "Stock Log API", description = "재고 로그 조회 API 입니다.")
public class StockLogController {
    private final StockLogService stockLogService;

    @GetMapping
    @Operation(summary = "재고 로그 조회", description = "재고로그를 각각의 조건에 따라 조회합니다")
    public ResponseEntity<ResponseDto<PageResponseDto<StockLogResponseDto>>> getStockLogs(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(required = false) String employeeName,
            @RequestParam(required = false) String bookTitle,
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false)StockActionType type,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end){
        ResponseDto<PageResponseDto<StockLogResponseDto>> response = stockLogService.getFilteredStockLogs(page,size, employeeName, bookTitle, branchId, keyword,type,start,end);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "재고 로그 조회", description = "재고로그를 단건 조회합니다")
    @GetMapping("/{stockLogId}")
    public ResponseEntity<ResponseDto<StockLogResponseDto>> getStockLogById(
            @PathVariable Long stockLogId){
        ResponseDto<StockLogResponseDto> stockLog = stockLogService.getStockLogById(stockLogId);
        return ResponseEntity.status(HttpStatus.OK).body(stockLog);
    }
}
