package com.example.bookhub_back.dto.purchaseOrder.request;

import com.example.bookhub_back.common.enums.PurchaseOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderApproveRequestDto {
    PurchaseOrderStatus status;
}
