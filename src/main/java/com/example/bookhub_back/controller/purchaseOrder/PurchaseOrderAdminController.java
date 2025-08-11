package com.example.bookhub_back.controller.purchaseOrder;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.purchaseOrder.request.PurchaseOrderApproveRequestDto;
import com.example.bookhub_back.dto.purchaseOrder.response.PurchaseOrderResponseDto;
import com.example.bookhub_back.security.auth.EmployeePrincipal;
import com.example.bookhub_back.service.purchaseOrder.PurchaseOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(ApiMappingPattern.ADMIN_API+"/purchase-order-approval")
@RequiredArgsConstructor
@Tag(name = "Purchase Order Approval API", description = "발주 승인 API 입니다.")
public class PurchaseOrderAdminController {


    private final PurchaseOrderService purchaseOrderService;
    @GetMapping("/requested")
    public ResponseEntity<ResponseDto<List<PurchaseOrderResponseDto>>> getAllPurchaseOrdersRequested() {
        ResponseDto<List<PurchaseOrderResponseDto>> response = purchaseOrderService.getAllPurchaseOrdersRequested();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/approval/{purchaseOrderId}")
    public ResponseEntity<ResponseDto<PurchaseOrderResponseDto>> approvePurchaseOrder(
            @AuthenticationPrincipal EmployeePrincipal principal,
            @PathVariable Long purchaseOrderId,
            @RequestBody PurchaseOrderApproveRequestDto dto
    ){
        ResponseDto<PurchaseOrderResponseDto> response = purchaseOrderService.approvePurchaseOrder(principal, purchaseOrderId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
