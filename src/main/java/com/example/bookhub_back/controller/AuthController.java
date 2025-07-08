package com.example.bookhub_back.controller;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.auth.request.SignInRequestDto;
import com.example.bookhub_back.dto.auth.response.SignInResponseDto;
import com.example.bookhub_back.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiMappingPattern.AUTH_API)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<SignInResponseDto>> login(@Valid @RequestBody SignInRequestDto dto) {
        ResponseDto<SignInResponseDto> responseDto = authService.login(dto);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }
}
