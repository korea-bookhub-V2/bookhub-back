package com.example.bookhub_back.controller.statistics;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.statistics.response.salesQuantity.BestSellerDto;
import com.example.bookhub_back.dto.statistics.response.salesQuantity.CategorySalesQuantityDto;
import com.example.bookhub_back.dto.statistics.response.salesQuantity.SalesQuantityStatisticsDto;
import com.example.bookhub_back.service.statistics.SalesQuantityStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@Tag(name = "SalesQuantityStatistics API", description = "판매량 통계 관련 API입니다.")

public class SalesQuantityStatisticsController {
    private final SalesQuantityStatisticsService salesQuantityStatisticsService;
    private final String BEST_SELLERS_API = ApiMappingPattern.MANAGER_API + ("/statistics/sales-quantity/bestseller");
    private final String SALES_QUANTITY_API = ApiMappingPattern.ADMIN_API + ("/statistics/sales-quantity");

    @Operation(summary = "Top100 베스트셀러 조회", description = "총합 베스트셀러(100위까지)를 조회합니다.")
    @GetMapping(BEST_SELLERS_API)
    public ResponseEntity<ResponseDto<List<BestSellerDto>>> getTop100BestSellers() {
        ResponseDto<List<BestSellerDto>> responseDto = salesQuantityStatisticsService.getTop100BestSellers();
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "주간 베스트셀러 조회", description = "기간별(주간) 베스트셀러(100위까지)를 조회합니다.")
    @GetMapping(BEST_SELLERS_API + ("/weekly"))
    public ResponseEntity<ResponseDto<List<BestSellerDto>>> getWeeklyBestSellers() {
        ResponseDto<List<BestSellerDto>> response= salesQuantityStatisticsService.getWeeklyBestSellers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "월간 베스트셀러 조회", description = "기간별(월간) 베스트셀러(100위까지)를 조회합니다.")
    @GetMapping(BEST_SELLERS_API + ("/monthly"))
    public ResponseEntity<ResponseDto<List<BestSellerDto>>> getMonthlyBestSellers() {
        ResponseDto<List<BestSellerDto>> response = salesQuantityStatisticsService.getMonthlyBestSellers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "연간 베스트셀러 조회", description = "기간별(연간) 베스트셀러(100위까지)를 조회합니다.")
    @GetMapping(BEST_SELLERS_API + ("/yearly"))
    public ResponseEntity<ResponseDto<List<BestSellerDto>>> getYearlyBestSellers() {
        ResponseDto<List<BestSellerDto>> response = salesQuantityStatisticsService.getYearlyBestSellers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "카테고리별 베스트셀러 조회", description = "카테고리별 베스트셀러(20위까지)를 조회합니다.")
    @GetMapping(BEST_SELLERS_API + ("/category/{categoryId}"))
    public ResponseEntity<ResponseDto<List<BestSellerDto>>> getBestSellersByCategory(
            @PathVariable Long categoryId) {
        ResponseDto<List<BestSellerDto>> response = salesQuantityStatisticsService.getBestSellersByCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "일간 총 판매 수량 통계 조회", description = "특정 월의 일별 총 판매 수량을 조회합니다.")
    @GetMapping(SALES_QUANTITY_API + ("/daily"))
    public ResponseEntity<ResponseDto<List<SalesQuantityStatisticsDto>>> getDailySalesQuantity(
            @RequestParam("month") int month
    ) {
        ResponseDto<List<SalesQuantityStatisticsDto>> response = salesQuantityStatisticsService.getDailySalesQuantity(month);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "주간 총 판매 수량 통계 조회", description = "특정 월의 주간별 총 판매 수량을 조회합니다.")
    @GetMapping(SALES_QUANTITY_API + ("/weekly"))
    public ResponseEntity<ResponseDto<List<SalesQuantityStatisticsDto>>> getWeeklySalesQuantity(
            @RequestParam("year") int year,
            @RequestParam("month") int month
    ) {
        ResponseDto<List<SalesQuantityStatisticsDto>> response = salesQuantityStatisticsService.getWeeklySalesQuantity(year, month);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "월간 총 판매 수량 통계 조회", description = "특정 연도의 월별 총 판매 수량을 조회합니다.")
    @GetMapping(SALES_QUANTITY_API + ("/monthly"))
    public ResponseEntity<ResponseDto<List<SalesQuantityStatisticsDto>>> getMonthlySalesQuantity(
            @RequestParam int year
    ) {
        ResponseDto<List<SalesQuantityStatisticsDto>> response = salesQuantityStatisticsService.getMonthlySalesQuantity(year);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "카테고리별 총 판매 수량 통계 조회", description = "특정 카테고리의 한달간 총 판매 수량을 조회합니다.")
    @GetMapping(SALES_QUANTITY_API + ("/category"))
    public ResponseEntity<ResponseDto<List<CategorySalesQuantityDto>>> getCategorySalesQuantity() {
        ResponseDto<List<CategorySalesQuantityDto>> response = salesQuantityStatisticsService.getSalesQuantityByCategory();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "할인항목별 총 판매 수량 통계 조회", description = "특정 할인항목의 총 판매 수량을 특정 연도, 분기 단위로 조회합니다.")
    @GetMapping(SALES_QUANTITY_API + ("/discount-policy"))
    public ResponseEntity<ResponseDto<List<SalesQuantityStatisticsDto>>> getDiscountPolicySalesQuantity(
            @RequestParam int year,
            @RequestParam int quarter
    ) {
        ResponseDto<List<SalesQuantityStatisticsDto>> response = salesQuantityStatisticsService.getSalesQuantityByPolicy(year, quarter);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "주간 총 매출 통계 조회", description = "지점별 총 판매 수량을 특정 연도, 월 단위로 조회합니다.")
    @GetMapping(SALES_QUANTITY_API + ("/branch"))
    public ResponseEntity<ResponseDto<List<SalesQuantityStatisticsDto>>> getSalesQuantityByBranch(
            @RequestParam int year,
            @RequestParam int month
    ) {
        ResponseDto<List<SalesQuantityStatisticsDto>> response = salesQuantityStatisticsService.getSalesQuantityByBranch(year, month);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
