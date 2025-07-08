package com.example.bookhub_back.dto.policy.response;

import com.example.bookhub_back.common.enums.PolicyType;

import java.time.LocalDate;

public class PolicyDetailResponseDto {
    private String policyTitle;
    private String policyDescription;
    private PolicyType policyType;
    private Integer totalPriceAchieve;
    private Integer discountPercent;
    private LocalDate startDate;
    private LocalDate endDate;
}
