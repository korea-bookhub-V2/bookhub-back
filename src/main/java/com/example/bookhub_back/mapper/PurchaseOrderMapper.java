package com.example.bookhub_back.mapper;

import com.example.bookhub_back.common.enums.PurchaseOrderStatus;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface PurchaseOrderMapper {
//    List<PurchaseOrderResponseDto> searchPurchaseOrder(@Param("employeeName") String employeeName, @Param("bookIsbn") String bookIsbn, @Param("purchaseOrderStatus") PurchaseOrderStatus purchaseOrderStatus);
}
