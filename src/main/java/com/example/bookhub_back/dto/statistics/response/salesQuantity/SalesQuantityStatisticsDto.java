package com.example.bookhub_back.dto.statistics.response.salesQuantity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesQuantityStatisticsDto {
    Long totalSales;
    LocalDateTime orderDate;
    Integer orderMonth;
    Integer yearWeek;
    LocalDateTime weekStartDate;
    LocalDateTime weekEndDate;
    String categoryName;
    String policyTitle;
    String branchName;
}
