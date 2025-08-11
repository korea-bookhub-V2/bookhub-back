package com.example.bookhub_back.controller.purchaseOrder;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.common.enums.PurchaseOrderStatus;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.purchaseOrder.request.PurchaseOrderApproveRequestDto;
import com.example.bookhub_back.dto.purchaseOrder.request.PurchaseOrderCreateRequestDto;
import com.example.bookhub_back.dto.purchaseOrder.request.PurchaseOrderRequestDto;
import com.example.bookhub_back.dto.purchaseOrder.response.PurchaseOrderResponseDto;
import com.example.bookhub_back.security.auth.EmployeePrincipal;
import com.example.bookhub_back.service.purchaseOrder.PurchaseOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.MANAGER_API+"/purchase-orders")
@RequiredArgsConstructor
@Tag(name = "Policy Manager API", description = "발주 API 입니다.")
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    @Operation(summary = "발주 생성", description = "발주 요청서를 작성합니다")
    @PostMapping
    public ResponseEntity<ResponseDto<List<PurchaseOrderResponseDto>>> createPurchaseOrder(
            @AuthenticationPrincipal EmployeePrincipal principal,
            @Valid @RequestBody PurchaseOrderCreateRequestDto dto
    ) {
        ResponseDto<List<PurchaseOrderResponseDto>> response = purchaseOrderService.createPurchaseOrder(principal, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "발주 요청서 조회", description = "조회 조건 없을 시 전체 조회 기능, 사용자 소속 지점 해당 발주서만 필터링")
    @GetMapping
    public ResponseEntity<ResponseDto<List<PurchaseOrderResponseDto>>> searchPurchaseOrder(
            @AuthenticationPrincipal EmployeePrincipal principal,
            @RequestParam(required = false) String employeeName,
            @RequestParam(required = false) String bookIsbn,
            @RequestParam(required = false) PurchaseOrderStatus purchaseOrderStatus
    ) {
        ResponseDto<List<PurchaseOrderResponseDto>> response = purchaseOrderService.searchPurchaseOrder(principal, employeeName, bookIsbn, purchaseOrderStatus);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "발주 요청서 수정", description = "발주량 수정")
    @PutMapping("/{purchaseOrderId}")
    public ResponseEntity<ResponseDto<PurchaseOrderResponseDto>> updatePurchaseOrder(
            @RequestBody PurchaseOrderRequestDto dto,
            @PathVariable Long purchaseOrderId
    ) {
        ResponseDto<PurchaseOrderResponseDto> response = purchaseOrderService.updatePurchaseOrder(dto, purchaseOrderId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "발주 요청서 삭제", description = "발주 요청서를 삭제합니다")
    @DeleteMapping("/{purchaseOrderId}")
    public ResponseEntity<ResponseDto<Void>> deletePurchaseOrder(
            @PathVariable Long purchaseOrderId
    ) {
        ResponseDto<Void> response = purchaseOrderService.deletePurchaseOrder(purchaseOrderId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
