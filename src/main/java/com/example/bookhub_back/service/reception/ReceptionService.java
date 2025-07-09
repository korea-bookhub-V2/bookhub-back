package com.example.bookhub_back.service.reception;

import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.reception.response.ReceptionCreateResponseDto;
import com.example.bookhub_back.dto.reception.response.ReceptionListResponseDto;

import java.util.List;

public interface ReceptionService {
    ResponseDto<ReceptionCreateResponseDto> createReception(ReceptionCreateResponseDto dto, String token);
    ResponseDto<Void> approveReception(Long id, String token);
    ResponseDto<List<ReceptionListResponseDto>> getPendingList(String token);
    ResponseDto<List<ReceptionListResponseDto>> getManagerConfirmedList(String token);
    ResponseDto<List<ReceptionListResponseDto>> getAdminConfirmedList(String branchName, String token);
}
