package com.example.bookhub_back.service.statistics;

import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.statistics.response.stocks.BranchStockBarChartDto;
import com.example.bookhub_back.dto.statistics.response.stocks.CategoryStockResponseDto;
import com.example.bookhub_back.dto.statistics.response.stocks.TimeStockChartResponseDto;
import com.example.bookhub_back.dto.statistics.response.stocks.ZeroStockResponseDto;

import java.util.List;

public interface StocksStatisticsService {
    ResponseDto<List<BranchStockBarChartDto>> getBranchStockBarChart(int year, int month);

    ResponseDto<List<TimeStockChartResponseDto>> getTimeStockStatistics(Long year);

    ResponseDto<List<ZeroStockResponseDto>> getZeroStockBooks();

    ResponseDto<List<CategoryStockResponseDto>> getCategoryStocks(String branchName);
}
