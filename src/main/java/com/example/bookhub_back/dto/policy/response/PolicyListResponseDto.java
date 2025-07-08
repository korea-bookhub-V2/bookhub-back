package com.example.bookhub_back.dto.policy.response;

import com.example.bookhub_back.common.enums.PolicyType;

import java.time.LocalDate;

public class PolicyListResponseDto {
    private Long policyId;
    private String policyTitle;
    private PolicyType policyType;
    private LocalDate startDate;
    private LocalDate endDate;
}
