package com.example.bookhub_back.dto.employee.request;

import com.example.bookhub_back.common.constants.RegexConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSignUpUpdateRequestDto {
    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = RegexConstants.PHONE_REGEX, message = "휴대폰 번호는 010으로 사작하고 8자리여야 합니다.")
    private String phoneNumber;
    private LocalDate birthDate;
    private Long branchId;
}
