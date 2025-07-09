package com.example.bookhub_back.service.alert;

import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.alert.request.AlertCreateRequestDto;
import com.example.bookhub_back.dto.alert.request.AlertReadRequestDto;
import com.example.bookhub_back.dto.alert.response.AlertResponseDto;

import java.util.List;

public interface AlertService {
    ResponseDto<AlertResponseDto> createAlert(AlertCreateRequestDto dto);
    ResponseDto<List<AlertResponseDto>> getAllAlerts(Long employeeId, String token);
    ResponseDto<List<AlertResponseDto>> getUnreadAlerts(Long employeeId, String token);
    ResponseDto<Void> readAlert(AlertReadRequestDto dto);
}
