package com.example.bookhub_back.mapper;

import com.example.bookhub_back.dto.statistics.response.stocks.BranchStockBarChartDto;
import com.example.bookhub_back.dto.statistics.response.stocks.CategoryStockResponseDto;
import com.example.bookhub_back.dto.statistics.response.stocks.TimeStockChartResponseDto;
import com.example.bookhub_back.dto.statistics.response.stocks.ZeroStockResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StocksStatisticsMapper {
    List<BranchStockBarChartDto> findBranchStockSummaryWithTotal(
        @Param("year") int year,
        @Param("month") int month
    );

    List<TimeStockChartResponseDto> findTimeStockStatisticsZeroFilled(
        @Param("year") int year,
        @Param("currentMonth") int currentMonth
    );

    List<ZeroStockResponseDto> findZeroStockStatistics();

    List<CategoryStockResponseDto> findCategoryTop9EtcByBranchName(
        @Param("branchName") String branchName
    );
}
