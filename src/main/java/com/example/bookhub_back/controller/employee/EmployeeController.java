package com.example.bookhub_back.controller.employee;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.common.enums.Status;
import com.example.bookhub_back.dto.PageResponseDto;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.employee.request.EmployeeOrganizationUpdateRequestDto;
import com.example.bookhub_back.dto.employee.request.EmployeeSignUpApprovalRequestDto;
import com.example.bookhub_back.dto.employee.request.EmployeeStatusUpdateRequestDto;
import com.example.bookhub_back.dto.employee.response.EmployeeListResponseDto;
import com.example.bookhub_back.dto.employee.response.EmployeeResponseDto;
import com.example.bookhub_back.dto.employee.response.EmployeeSignUpApprovalResponseDto;
import com.example.bookhub_back.security.auth.EmployeePrincipal;
import com.example.bookhub_back.service.employee.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.ADMIN_API + "/employees")
@RequiredArgsConstructor
@Tag(name = "Employee API", description = "사원 관련 API입니다.")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Operation(summary = "사원 검색 조회", description = "이름, 지점 ID, 직급 ID, 권한 ID, 재직 상태로 사원 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<ResponseDto<PageResponseDto<EmployeeListResponseDto>>> searchEmployee(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "10") @Min(1) int size,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Long branchId,
        @RequestParam(required = false) Long positionId,
        @RequestParam(required = false) Long authorityId,
        @RequestParam(required = false) Status status
    ) {
        ResponseDto<PageResponseDto<EmployeeListResponseDto>> responseDto = employeeService.searchEmployee(page, size, name, branchId, positionId, authorityId, status);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @Operation(summary = "사원 상세 조회", description = "사원 ID로 직원 상세를 조회합니다.")
    @GetMapping("/{employeeId}")
    public ResponseEntity<ResponseDto<EmployeeResponseDto>> getEmployeeById(@PathVariable Long employeeId) {
        ResponseDto<EmployeeResponseDto> responseDto = employeeService.getEmployeeById(employeeId);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @Operation(summary = "회원가입 승인 대기 중인 사원 조회", description = "회원가입 승인 대기 중인 사원을 조회합니다.")
    @GetMapping("/approval")
    public ResponseEntity<ResponseDto<PageResponseDto<EmployeeSignUpApprovalResponseDto>>> getPendingEmployee(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "10") @Min(1) int size
    ) {
        ResponseDto<PageResponseDto<EmployeeSignUpApprovalResponseDto>> responseDto = employeeService.getPendingEmployee(page, size);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @Operation(summary = "회원가입 승인 상태 수정", description = "관리자만 회원가입 승인 또는 거절 합니다.")
    @PutMapping("/{employeeId}/approval")
    public ResponseEntity<ResponseDto<EmployeeSignUpApprovalResponseDto>> updateApproval(
        @PathVariable Long employeeId,
        @Valid @RequestBody EmployeeSignUpApprovalRequestDto dto,
        @AuthenticationPrincipal EmployeePrincipal employeePrincipal
    ) {
        ResponseDto<EmployeeSignUpApprovalResponseDto> responseDto = employeeService.updateApproval(employeeId, dto, employeePrincipal);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @Operation(summary = "사원 정보 수정", description = "관리자만 사원 정보를 수정합니다.")
    @PutMapping("/{employeeId}/organization-update")
    public ResponseEntity<ResponseDto<Void>> updateOrganization(
        @PathVariable Long employeeId,
        @RequestBody EmployeeOrganizationUpdateRequestDto dto,
        @AuthenticationPrincipal EmployeePrincipal employeePrincipal
    ) {
        ResponseDto<Void> responseDto = employeeService.updateOrganization(employeeId, dto, employeePrincipal);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @Operation(summary = "사원 재직 상태 수정", description = "관리자만 사원을 퇴직 상태로 수정합니다.")
    @PutMapping("/{employeeId}/status")
    public ResponseEntity<ResponseDto<Void>> updateStatus(
        @PathVariable Long employeeId,
        @Valid @RequestBody EmployeeStatusUpdateRequestDto dto,
        @AuthenticationPrincipal EmployeePrincipal employeePrincipal
    ) {
        ResponseDto<Void> responseDto = employeeService.updateStatus(employeeId, dto, employeePrincipal);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }
}
