package com.example.bookhub_back.service.purchaseOrder;

import com.example.bookhub_back.common.enums.PurchaseOrderStatus;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.purchaseOrder.request.PurchaseOrderApproveRequestDto;
import com.example.bookhub_back.dto.purchaseOrder.request.PurchaseOrderCreateRequestDto;
import com.example.bookhub_back.dto.purchaseOrder.request.PurchaseOrderRequestDto;
import com.example.bookhub_back.dto.purchaseOrder.response.PurchaseOrderResponseDto;
import com.example.bookhub_back.security.auth.EmployeePrincipal;
import jakarta.validation.Valid;

import java.util.List;

public interface PurchaseOrderService {
    ResponseDto<List<PurchaseOrderResponseDto>> createPurchaseOrder(EmployeePrincipal principal, @Valid PurchaseOrderCreateRequestDto dto);

    ResponseDto<PurchaseOrderResponseDto> updatePurchaseOrder(PurchaseOrderRequestDto dto, Long purchaseOrderId);

    ResponseDto<Void> deletePurchaseOrder(Long purchaseOrderId);

    ResponseDto<List<PurchaseOrderResponseDto>> searchPurchaseOrder(EmployeePrincipal principal, String employeeName, String bookIsbn, PurchaseOrderStatus purchaseOrderStatus);

    ResponseDto<PurchaseOrderResponseDto> approvePurchaseOrder(EmployeePrincipal principal, Long purchaseOrderId, PurchaseOrderApproveRequestDto dto);

    ResponseDto<List<PurchaseOrderResponseDto>> getAllPurchaseOrdersRequested();
}
