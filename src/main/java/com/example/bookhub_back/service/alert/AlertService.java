package com.example.bookhub_back.service.alert;

import com.example.bookhub_back.dto.PageResponseDto;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.alert.request.AlertCreateRequestDto;
import com.example.bookhub_back.dto.alert.request.AlertReadRequestDto;
import com.example.bookhub_back.dto.alert.response.AlertResponseDto;

import java.util.List;

public interface AlertService {
    ResponseDto<AlertResponseDto> createAlert(AlertCreateRequestDto dto);
    ResponseDto<List<AlertResponseDto>> getAllAlerts(Long employeeId, String token);
    ResponseDto<PageResponseDto<AlertResponseDto>> getUnreadAlerts(Long employeeId, String token, int page, int size);
    ResponseDto<Void> readAlert(AlertReadRequestDto dto);
}
