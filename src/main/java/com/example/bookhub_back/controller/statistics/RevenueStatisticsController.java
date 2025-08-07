package com.example.bookhub_back.controller.statistics;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.statistics.response.revenue.BranchRevenueResponseDto;
import com.example.bookhub_back.dto.statistics.response.revenue.MonthlyRevenueResponseDto;
import com.example.bookhub_back.dto.statistics.response.revenue.WeekdayRevenueResponseDto;
import com.example.bookhub_back.dto.statistics.response.revenue.WeeklyRevenueResponseDto;
import com.example.bookhub_back.service.statistics.RevenueStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.ADMIN_API+"/statistics/revenue")
@RequiredArgsConstructor
@Tag(name = "Revenue Statistics API", description = "매출 통계 API 입니다")
public class RevenueStatisticsController {
    private final RevenueStatisticsService revenueService;

    @Operation(summary = "요일별 매출", description = "월, 화, 수, 목, 금, 토, 일 각각의 요일별 매출 데이터 통계")
    @GetMapping("/weekday")
    public ResponseEntity<ResponseDto<List<WeekdayRevenueResponseDto>>> getWeekdayRevenue(
            @RequestParam int year,
            @RequestParam int quarter
    ){
        ResponseDto<List<WeekdayRevenueResponseDto>> revenue = revenueService.getWeekdayRevenue(year,quarter);
        return ResponseEntity.status(HttpStatus.OK).body(revenue);
    }

    @Operation(summary = "주별 매출", description = "해당 년, 월에 주별 매출 추이 통계")
    @GetMapping("/weekly")
    public ResponseEntity<ResponseDto<List<WeeklyRevenueResponseDto>>> getWeeklyRevenue(
            @RequestParam("year") int year,
            @RequestParam("month") int month
    ){
        ResponseDto<List<WeeklyRevenueResponseDto>> revenue = revenueService.getWeeklyRevenue(year, month);
        return ResponseEntity.status(HttpStatus.OK).body(revenue);
    }

    @Operation(summary = "월별 매출 통계", description = "해당 년도에 월별 매출 추이 통계")
    @GetMapping("/monthly")
    public ResponseEntity<ResponseDto<List<MonthlyRevenueResponseDto>>> getMonthlyRevenue(
            @RequestParam int year
    ){
        ResponseDto<List<MonthlyRevenueResponseDto>> revenue = revenueService.getMonthlyRevenue(year);
        return ResponseEntity.status(HttpStatus.OK).body(revenue);
    }

    @Operation(summary = "지점별 매출 통계", description = "특정 기간을 설정하면 해당 기간 내 지점별로 매출 통계")
    @GetMapping("/branch")
    public ResponseEntity<ResponseDto<List<BranchRevenueResponseDto>>> getBranchRevenue(
            @RequestParam("startDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam("endDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ){
        ResponseDto<List<BranchRevenueResponseDto>> revenue = revenueService.getBranchRevenue(startDate,endDate);
        return ResponseEntity.status(HttpStatus.OK).body(revenue);
    }
}
