package com.example.bookhub_back.controller.alert;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.PageResponseDto;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.alert.request.AlertCreateRequestDto;
import com.example.bookhub_back.dto.alert.request.AlertReadRequestDto;
import com.example.bookhub_back.dto.alert.response.AlertResponseDto;
import com.example.bookhub_back.security.auth.EmployeePrincipal;
import com.example.bookhub_back.service.alert.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.COMMON_API+"/alerts")
@RequiredArgsConstructor
@Tag(name = "Alert API", description = "알림 관련 API입니다.")

public class AlertController {
    private final AlertService alertService;

    @Operation(summary = "알림 생성", description = "알림을 생성합니다.")
    @PostMapping
    public ResponseEntity<ResponseDto<AlertResponseDto>> createAlert(@RequestBody AlertCreateRequestDto dto) {
        ResponseDto<AlertResponseDto> responseDto = alertService.createAlert(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "모든 알림 조회", description = "모든 알림을 조회합니다.")
    @GetMapping("/all/{employeeId}")
    public ResponseEntity<ResponseDto<List<AlertResponseDto>>> getAllAlert(
            @AuthenticationPrincipal EmployeePrincipal employeePrincipal
    ) {
        Long employeeId = employeePrincipal.getEmployeeId();
        ResponseDto<List<AlertResponseDto>> responseDto = alertService.getAllAlerts(employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "미확인 알림 조회", description = "읽음처리 되지 않은 알림을 조회합니다.")
    @GetMapping("/unread/{employeeId}")
    public ResponseEntity<ResponseDto<PageResponseDto<AlertResponseDto>>> getUnreadAlert(
            @AuthenticationPrincipal EmployeePrincipal employeePrincipal,
            @RequestParam int page,
            @RequestParam int size
    ) {
        Long employeeId = employeePrincipal.getEmployeeId();
        ResponseDto<PageResponseDto<AlertResponseDto>> responseDto = alertService.getUnreadAlerts(employeeId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "알림 확인", description = "알림을 읽음처리합니다.")
    @PutMapping("/read")
    public ResponseEntity<ResponseDto<Void>> readAlert(@RequestBody AlertReadRequestDto dto) {
        ResponseDto<Void> responseDto = alertService.readAlert(dto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
