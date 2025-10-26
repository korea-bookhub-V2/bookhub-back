package com.example.bookhub_back.service.statistics;

import com.example.bookhub_back.common.constants.ResponseCode;
import com.example.bookhub_back.common.constants.ResponseMessageKorean;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.statistics.response.stocks.BranchStockBarChartDto;
import com.example.bookhub_back.dto.statistics.response.stocks.CategoryStockResponseDto;
import com.example.bookhub_back.dto.statistics.response.stocks.TimeStockChartResponseDto;
import com.example.bookhub_back.dto.statistics.response.stocks.ZeroStockResponseDto;
import com.example.bookhub_back.mapper.StocksStatisticsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StocksStatisticsServiceImpl implements StocksStatisticsService {

    private final StocksStatisticsMapper stocksStatisticsMapper;

    @Override
    public ResponseDto<List<BranchStockBarChartDto>> getBranchStockBarChart(int year, int month) {
        List<BranchStockBarChartDto> rows = stocksStatisticsMapper.findBranchStockSummaryWithTotal(year, month);

        List<BranchStockBarChartDto> result = rows.stream()
            .map(r -> BranchStockBarChartDto.builder()
                .branchName(r.getBranchName())
                .inAmount(r.getInAmount() == null ? 0L : r.getInAmount())
                .outAmount(r.getOutAmount() == null ? 0L : r.getOutAmount())
                .lossAmount(r.getLossAmount() == null ? 0L : r.getLossAmount())
                .build())
            .toList();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, result);
    }

    @Override
    public ResponseDto<List<TimeStockChartResponseDto>> getTimeStockStatistics(Long year) {
        int currentMonth = (LocalDate.now().getYear() == year ? LocalDate.now().getMonthValue() : 12);
        List<TimeStockChartResponseDto> rows = stocksStatisticsMapper.findTimeStockStatisticsZeroFilled(year.intValue(), currentMonth);

        List<TimeStockChartResponseDto> result = rows.stream()
            .map(r -> TimeStockChartResponseDto.builder()
                .branchName(r.getBranchName())
                .month(r.getMonth())
                .inAmount(r.getInAmount() == null ? 0L : r.getInAmount())
                .lossAmount(r.getLossAmount() == null ? 0L : r.getLossAmount())
                .build())
            .toList();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, result);
    }

    @Override
    public ResponseDto<List<ZeroStockResponseDto>> getZeroStockBooks() {
        List<ZeroStockResponseDto> rows = stocksStatisticsMapper.findZeroStockStatistics();
        List<ZeroStockResponseDto> result = rows.stream()
            .map(r -> ZeroStockResponseDto.builder()
                .branchName(r.getBranchName())
                .zeroStockCount(r.getZeroStockCount())
                .build())
            .toList();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, result);
    }

    @Override
    public ResponseDto<List<CategoryStockResponseDto>> getCategoryStocks(String branchName) {
        List<CategoryStockResponseDto> rows = stocksStatisticsMapper.findCategoryTop9EtcByBranchName(branchName);

        List<CategoryStockResponseDto> result = rows.stream()
            .map(r -> new CategoryStockResponseDto(r.getCategoryName(), r.getQuantity()))
            .toList();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, result);
    }
}
