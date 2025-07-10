package com.example.bookhub_back.controller.auth;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.auth.request.SignInRequestDto;
import com.example.bookhub_back.dto.auth.request.SignUpRequestDto;
import com.example.bookhub_back.dto.auth.response.SignInResponseDto;
import com.example.bookhub_back.service.auth.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.AUTH_API)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<Void>> signUp(@Valid @RequestBody SignUpRequestDto dto) {
        ResponseDto<Void> responseDto = authService.signUp(dto);
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<SignInResponseDto>> login(@Valid @RequestBody SignInRequestDto dto) {
        ResponseDto<SignInResponseDto> responseDto = authService.login(dto);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @GetMapping("/login-id-exists")
    public ResponseEntity<ResponseDto<Void>> checkLoginIdDuplicate(@RequestParam String loginId) {
        ResponseDto<Void> responseDto = authService.checkLoginIdDuplicate(loginId);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @GetMapping("/email-exists")
    public ResponseEntity<ResponseDto<Void>> checkEmailDuplicate(@RequestParam String email) {
        ResponseDto<Void> responseDto = authService.checkEmailDuplicate(email);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @GetMapping("/phone-number-exists")
    public ResponseEntity<ResponseDto<Void>> checkPhoneNumberDuplicate(@RequestParam String phoneNumber) {
        ResponseDto<Void> responseDto = authService.checkPhoneNumberDuplicate(phoneNumber);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Void>> logout (HttpServletResponse response) {
        ResponseDto<Void> responseDto = authService.logout(response);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }
}
