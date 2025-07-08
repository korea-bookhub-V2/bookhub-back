package com.example.bookhub_back.dto.policy.request;

import com.example.bookhub_back.common.enums.PolicyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class PolicyCreateRequestDto {
    @NotBlank
    private String policyTitle;
    private String policyDescription;
    @NotNull
    private PolicyType policyType;
    private Integer totalPriceAchieve;
    @NotNull
    private Integer discountPercent;
    private LocalDate startDate;
    private LocalDate endDate;
}
