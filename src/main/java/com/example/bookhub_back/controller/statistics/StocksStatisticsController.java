package com.example.bookhub_back.controller.statistics;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.statistics.projection.ZeroStockProjection;
import com.example.bookhub_back.dto.statistics.response.stocks.BranchStockBarChartDto;
import com.example.bookhub_back.dto.statistics.response.stocks.CategoryStockResponseDto;
import com.example.bookhub_back.dto.statistics.response.stocks.TimeStockChartResponseDto;
import com.example.bookhub_back.service.statistics.StocksStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.ADMIN_API + "/statistics/stocks")
@RequiredArgsConstructor
@Tag(name = "Stock Statistics API", description = "재고 통계 관련 API입니다.")
public class StocksStatisticsController {
    private final StocksStatisticsService stocksStatisticsService;

    @Operation(summary = "지점별 재고 통계 조회", description = "년도, 월로 재고 통계를 조회힙니다.")
    @GetMapping("/branch")
    public ResponseEntity<ResponseDto<List<BranchStockBarChartDto>>> getBranchStockBarChart(
        @RequestParam int year,
        @RequestParam int month
    ) {
        ResponseDto<List<BranchStockBarChartDto>> stockStatistics = stocksStatisticsService.getBranchStockBarChart(year, month);
        return ResponseEntity.status(HttpStatus.OK).body(stockStatistics);
    }

    @Operation(summary = "월별 지점 재고 통계 조회", description = "년도로 지점별 월 재고 통계를 조회합니다.")
    @GetMapping("/time")
    public ResponseEntity<ResponseDto<List<TimeStockChartResponseDto>>> getTimeStockStatistics(
        @RequestParam Long year
    ) {
        ResponseDto<List<TimeStockChartResponseDto>> revenue = stocksStatisticsService.getTimeStockStatistics(year);
        return ResponseEntity.status(HttpStatus.OK).body(revenue);
    }

    @Operation(summary = "지점별 재고가 0인 재고 통계 조회", description = "지점별 재고가 0인 재고 통게를 조회합니다.")
    @GetMapping("/zero")
    public ResponseEntity<ResponseDto<List<ZeroStockProjection>>> getZeroStockBooks() {
        ResponseDto<List<ZeroStockProjection>> revenue = stocksStatisticsService.getZeroStockBooks();
        return ResponseEntity.status(HttpStatus.OK).body(revenue);
    }

    @Operation(summary = "카테고리별 재고 통계 조회", description = "지점의 카테로리별 남은 재고 순위 통계를 조회합니다.")
    @GetMapping("/category")
    public ResponseEntity<ResponseDto<List<CategoryStockResponseDto>>> getCategoryStocks(
        @RequestParam String branchName
    ) {
        ResponseDto<List<CategoryStockResponseDto>> revenue = stocksStatisticsService.getCategoryStocks(branchName);
        return ResponseEntity.status(HttpStatus.OK).body(revenue);
    }
}
