package com.example.bookhub_back.controller.auth;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.auth.request.*;
import com.example.bookhub_back.dto.auth.response.SignInResponseDto;
import com.example.bookhub_back.dto.employee.request.EmployeeSignUpUpdateRequestDto;
import com.example.bookhub_back.service.auth.AuthService;
import com.example.bookhub_back.service.mail.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiMappingPattern.AUTH_API)
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "회원 가입 관련 API입니다.")
public class AuthController {
    private final AuthService authService;
    private final MailService mailService;

    @Operation(summary = "회원가입", description = "신규 사원이 ERP 시스템 회원 가입합니다.")
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<Void>> signUp(@Valid @RequestBody SignUpRequestDto dto) {
        ResponseDto<Void> responseDto = authService.signUp(dto);
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, responseDto);
    }

    @Operation(summary = "로그인", description = "ERP 시스템에 로그인 합니다.")
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<SignInResponseDto>> login(@Valid @RequestBody SignInRequestDto dto) {
        ResponseDto<SignInResponseDto> responseDto = authService.login(dto);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @Operation(summary = "아이디 중복 확인", description = "회원가입 시 아이디 중복을 확인합니다.")
    @GetMapping("/login-id-exists")
    public ResponseEntity<ResponseDto<Void>> checkLoginIdDuplicate(@RequestParam String loginId) {
        ResponseDto<Void> responseDto = authService.checkLoginIdDuplicate(loginId);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @Operation(summary = "이메일 중복 확인", description = "회원가입 시 이메일 중복을 확인합니다.")
    @GetMapping("/email-exists")
    public ResponseEntity<ResponseDto<Void>> checkEmailDuplicate(@RequestParam String email) {
        ResponseDto<Void> responseDto = authService.checkEmailDuplicate(email);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @Operation(summary = "전화번호 중복 확인", description = "회원가입 시 전화번호 중복을 확인합니다.")
    @GetMapping("/phone-number-exists")
    public ResponseEntity<ResponseDto<Void>> checkPhoneNumberDuplicate(@RequestParam String phoneNumber) {
        ResponseDto<Void> responseDto = authService.checkPhoneNumberDuplicate(phoneNumber);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @Operation(summary = "로그아웃", description = "ERP 시스템에서 로그아웃합니다.")
    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Void>> logout(HttpServletResponse response) {
        ResponseDto<Void> responseDto = authService.logout(response);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @Operation(summary = "아이디 찾기 이메일 전송", description = "아이디를 찾기 위해 이메일 인증을 보냅니다.")
    @PostMapping("/login-id-find/email")
    public Mono<ResponseEntity<ResponseDto<String>>> SendEmailFindId(@Valid @RequestBody LoginIdFindSendEmailRequestDto dto) {
        return mailService.sendEmailFindId(dto);
    }

    @Operation(summary = "아이디 조회", description = "이메일 인증을 통한 아이디 조회")
    @GetMapping("/login-id-find")
    public Mono<ResponseEntity<ResponseDto<String>>> verifyEmailId(@RequestParam String token) {
        return mailService.verifyEmailId(token);
    }

    @Operation(summary = "비밀번호 변경 이메일 전송", description = "비밀번호 변경을 위해 이메일 인증을 보냅니다.")
    @PostMapping("/password-change/email")
    public Mono<ResponseEntity<ResponseDto<String>>> sendEmailResetPassword(@Valid @RequestBody PasswordFindSendEmailRequestDto dto) {
        return mailService.sendEmailResetPassword(dto);
    }

    @Operation(summary = "비밀번호 변경 이메일 인증 확인", description = "비밀번호 변경을 위해 이메일 인증을 확인합니다.")
    @GetMapping("/password-change")
    public Mono<ResponseEntity<ResponseDto<String>>> verifyLoginIdPassword(@RequestParam String token) {
        return mailService.verifyLoginIdPassword(token);
    }

    @Operation(summary = "비밀번호 변경", description = "비밀번호를 변경합니다.")
    @PutMapping("/password-change")
    public Mono<ResponseEntity<ResponseDto<String>>> passwordChange(@RequestParam String token, @Valid @RequestBody PasswordResetRequestDto dto) {
        return mailService.passwordChange(token, dto);
    }

    @Operation(summary = "회원가입 승인 결과 이메일 전송", description = "회원가입 승인 결과를 이메일로 전송합니다. (사원 정부 불일치로 거절할 경우 이메일 인증을 보냅니다.)")
    @PostMapping("/employees/{approvalId}/approve")
    public Mono<ResponseEntity<ResponseDto<String>>> sendEmailSignUpResult(@PathVariable Long approvalId) {
        return mailService.sendEmailSignUpResult(approvalId);
    }

    @Operation(summary = "사원 정보 불일치 시 이메일 인증 확인", description = "회원가입 거절 사유가 사원 정보 불일치 시 이메일 인증 확인")
    @GetMapping("/employees/approve")
    public Mono<ResponseEntity<ResponseDto<String>>> verifyEmployeeUpdate(@RequestParam String token) {
        return mailService.verifyEmployeeUpdate(token);
    }

    @Operation(summary = "사원 정보 수정", description = "사원 정보를 수정합니다. (수정 완료 후 재승인 시도를 합니다.)")
    @PutMapping("/employees/approve")
    public Mono<ResponseEntity<ResponseDto<String>>> employeeUpdate(@RequestParam String token, @RequestBody EmployeeSignUpUpdateRequestDto dto) {
        return mailService.employeeUpdate(token, dto);
    }
}
