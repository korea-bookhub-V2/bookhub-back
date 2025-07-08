package com.example.bookhub_back.service;

import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.auth.request.SignInRequestDto;
import com.example.bookhub_back.dto.auth.response.SignInResponseDto;
import jakarta.validation.Valid;

public interface AuthService {
    ResponseDto<SignInResponseDto> login(@Valid SignInRequestDto dto);
}
