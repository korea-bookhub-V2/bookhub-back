package com.example.bookhub_back.dto.employee.request;

import com.example.bookhub_back.common.enums.ExitReason;
import com.example.bookhub_back.common.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeStatusUpdateRequestDto {
    @NotBlank
    private Status status;
    private ExitReason exitReason;
}
