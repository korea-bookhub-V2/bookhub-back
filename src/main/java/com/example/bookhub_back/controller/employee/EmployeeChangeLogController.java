package com.example.bookhub_back.controller.employee;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.common.enums.ChangeType;
import com.example.bookhub_back.dto.PageResponseDto;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.employee.response.EmployeeChangeLogListResponseDto;
import com.example.bookhub_back.service.employee.EmployeeChangeLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.ADMIN_API + "/employee-change-logs")
@RequiredArgsConstructor
@Tag(name = "Employee Change Log API", description = "사원 정보 수정 로그 관련 API입니다.")
public class EmployeeChangeLogController {
    private final EmployeeChangeLogService employeeChangeLogService;

    @Operation(summary = "사원 정보 로그 조회", description = "사원 이름, 관리자 이름, 변경 타입, 변경 날짜로 사원 정보 변경 로그를 조회합니다.")
    @GetMapping
    public ResponseEntity<ResponseDto<PageResponseDto<EmployeeChangeLogListResponseDto>>> searchEmployeeChangeLogs(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "10") @Min(1) int size,
        @RequestParam(required = false) String employeeName,
        @RequestParam(required = false) String authorizerName,
        @RequestParam(required = false) ChangeType changeType,
        @RequestParam(required = false) LocalDate startUpdatedAt,
        @RequestParam(required = false) LocalDate endUpdatedAt
    ) {
        ResponseDto<PageResponseDto<EmployeeChangeLogListResponseDto>> responseDto = employeeChangeLogService.searchEmployeeChangeLogs(page, size, employeeName, authorizerName, changeType, startUpdatedAt, endUpdatedAt);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }
}
