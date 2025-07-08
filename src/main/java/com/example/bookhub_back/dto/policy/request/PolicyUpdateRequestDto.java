package com.example.bookhub_back.dto.policy.request;

import java.time.LocalDate;

public class PolicyUpdateRequestDto {
    private String policyDescription;
    private Integer totalPriceAchieve;
    private Integer discountPercent;
    private LocalDate startDate;
    private LocalDate endDate;
}
