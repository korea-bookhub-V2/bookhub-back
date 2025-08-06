package com.example.bookhub_back.controller.employee;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.common.enums.IsApproved;
import com.example.bookhub_back.dto.PageResponseDto;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.employee.response.EmployeeSignUpApprovalsResponseDto;
import com.example.bookhub_back.service.employee.EmployeeSignupApprovalService;
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
@RequestMapping(ApiMappingPattern.ADMIN_API + "/employee-signup-approvals")
@RequiredArgsConstructor
@Tag(name = "Employee SignUp Approval API", description = "사원 회원가입 승인 로그 관련 API입니다.")
public class EmployeeSignUpApprovalController {
    private final EmployeeSignupApprovalService employeeSignupApprovalService;

    @Operation(summary = "사원 회원가입 승인 로그", description = "사원 이름, 관리자 이름, 승인 결과, 거절 사유, 변경 날짜로 사원 회원가입 승인 로그를 조회합니다.")
    @GetMapping
    public ResponseEntity<ResponseDto<PageResponseDto<EmployeeSignUpApprovalsResponseDto>>> searchSignUpApproval(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "10") @Min(1) int size,
        @RequestParam(required = false) String employeeName,
        @RequestParam(required = false) IsApproved isApproved,
        @RequestParam(required = false) String deniedReason,
        @RequestParam(required = false) String authorizerName,
        @RequestParam(required = false) LocalDate startUpdatedAt,
        @RequestParam(required = false) LocalDate endUpdatedAt
    ) {
        ResponseDto<PageResponseDto<EmployeeSignUpApprovalsResponseDto>> responseDto = employeeSignupApprovalService.searchSignUpApproval(
            page, size, employeeName, isApproved, deniedReason, authorizerName, startUpdatedAt, endUpdatedAt);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }
}