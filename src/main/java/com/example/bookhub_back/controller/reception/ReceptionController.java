package com.example.bookhub_back.controller.reception;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.PageResponseDto;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.reception.request.ReceptionCreateRequestDto;
import com.example.bookhub_back.dto.reception.response.ReceptionCreateResponseDto;
import com.example.bookhub_back.dto.reception.response.ReceptionListResponseDto;
import com.example.bookhub_back.security.auth.EmployeePrincipal;
import com.example.bookhub_back.service.reception.ReceptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Reception API", description = "발주 수령 관련 API입니다.")

public class ReceptionController {
    private final ReceptionService receptionService;

    @Operation(summary = "수령 리스트 생성", description = "발주 요청을 승인받으면 수령 리스트를 생성합니다.")
    @PostMapping(ApiMappingPattern.ADMIN_API + "/reception")
    public ResponseEntity<ResponseDto<ReceptionCreateResponseDto>> createReception(
            @RequestBody ReceptionCreateRequestDto dto,
            @AuthenticationPrincipal EmployeePrincipal employeePrincipal
    ) {
        Long branchId = employeePrincipal.getBranchId();
        ResponseDto<ReceptionCreateResponseDto> reception = receptionService.createReception(dto, branchId);
        return ResponseEntity.status(HttpStatus.CREATED).body(reception);
    }

    @Operation(summary = "수령 확인처리", description = "수령 항목을 수령확인 처리합니다.")
    @PutMapping(ApiMappingPattern.MANAGER_API + "/reception/approve/{id}")
    public ResponseEntity<ResponseDto<Void>> approveReception(
            @PathVariable Long id,
            @AuthenticationPrincipal EmployeePrincipal employeePrincipal
    ) {
        Long employeeId = employeePrincipal.getEmployeeId();
        ResponseDto<Void> responseDto = receptionService.approveReception(id, employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "수령 확인 대기 리스트 조회", description = "아직 수령확인 처리하지 않은 리스트를 조회합니다.")
    @GetMapping(ApiMappingPattern.MANAGER_API + "/reception/pending")
    public ResponseEntity<ResponseDto<PageResponseDto<ReceptionListResponseDto>>> getPendingReceptions(
            @AuthenticationPrincipal EmployeePrincipal employeePrincipal,
            @RequestParam int page,
            @RequestParam int size
    ) {
        String loginId = employeePrincipal.getLoginId();
        ResponseDto<PageResponseDto<ReceptionListResponseDto>> reception = receptionService.getPendingList(loginId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(reception);
    }

    @Operation(summary = "지점 수령 확인 기록 조회", description = "해당 지점에서 수령 확인 처리된 기록을 조회합니다.")
    @GetMapping(ApiMappingPattern.MANAGER_API + "/reception/confirmed")
    public ResponseEntity<ResponseDto<PageResponseDto<ReceptionListResponseDto>>> getManagerConfirmedReceptions(
            @AuthenticationPrincipal EmployeePrincipal employeePrincipal,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        String loginId = employeePrincipal.getLoginId();
        ResponseDto<PageResponseDto<ReceptionListResponseDto>> reception = receptionService.getManagerConfirmedList(loginId, page, size, startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK).body(reception);
    }

    @Operation(summary = "모든 지점 수령 기록 조회", description = "관리자는 모든 지점의 수령 기록을 조회합니다.")
    @GetMapping(ApiMappingPattern.ADMIN_API + "/reception/logs")
    public ResponseEntity<ResponseDto<PageResponseDto<ReceptionListResponseDto>>> getAdminConfirmedReceptions(
            @RequestParam(required = false) String branchName,
            @RequestParam(value = "bookIsbn", required = false) String isbn,
            @RequestParam int page,
            @RequestParam int size
    ) {
        ResponseDto<PageResponseDto<ReceptionListResponseDto>> reception = receptionService.getAdminConfirmedList(branchName, isbn, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(reception);
    }
}
