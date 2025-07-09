package com.example.bookhub_back.service.impl;

import com.example.bookhub_back.common.constants.ResponseCode;
import com.example.bookhub_back.common.constants.ResponseMessageKorean;
import com.example.bookhub_back.common.enums.IsApproved;
import com.example.bookhub_back.common.enums.Status;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.auth.request.SignInRequestDto;
import com.example.bookhub_back.dto.auth.request.SignUpRequestDto;
import com.example.bookhub_back.dto.auth.response.SignInResponseDto;
import com.example.bookhub_back.dto.employee.response.EmployeeResponseDto;
import com.example.bookhub_back.entity.*;
import com.example.bookhub_back.provider.JwtTokenProvider;
import com.example.bookhub_back.repository.*;
import com.example.bookhub_back.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmployeeSignUpApprovalRepository employeeSignUpApprovalRepository;
    private final PositionRepository positionRepository;
    private final AuthorityRepository authorityRepository;
    private final JwtTokenProvider jwtTokenProvider;
//    private final AlertService alertService;

    @Override
    @Transactional
    public ResponseDto<Void> signUp(SignUpRequestDto dto) {
        Employee employee = null;

        String loginId = dto.getLoginId();
        String password = dto.getPassword();
        String confirmPassword = dto.getConfirmPassword();
        String email = dto.getEmail();
        String phoneNumber = dto.getPhoneNumber();

        if (employeeRepository.existsByLoginId(loginId)) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_USER_ID, ResponseMessageKorean.DUPLICATED_USER_ID);
        }

        if (!password.equals(confirmPassword)) {
            return ResponseDto.fail(ResponseCode.NOT_MATCH_PASSWORD, ResponseMessageKorean.NOT_MATCH_PASSWORD);
        }

        if (employeeRepository.existsByEmail(email)) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_EMAIL, ResponseMessageKorean.DUPLICATED_EMAIL);
        }

        if (employeeRepository.existsByPhoneNumber(phoneNumber)) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_TEL_NUMBER, ResponseMessageKorean.DUPLICATED_TEL_NUMBER);
        }

        Branch branch = branchRepository.findById(dto.getBranchId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지점입니다."));

        Position position = positionRepository.findByPositionName("사원")
            .orElseGet(() -> positionRepository.save(Position.builder()
                .positionName("사원")
                .build()));

        Authority authority = authorityRepository.findByAuthorityName("STAFF")
            .orElseGet(() -> authorityRepository.save(Authority.builder()
                .authorityName("STAFF")
                .build()));

        Random random = new Random();
        long employeeNumber;

        do {
            int randomSixDigits = 100000 + random.nextInt(900000);
            String result = String.format("%02d", LocalDate.now().getYear() % 100) + randomSixDigits;
            employeeNumber = Long.parseLong(result);

        } while (employeeRepository.existsByEmployeeNumber(employeeNumber));

        String encodePassword = bCryptPasswordEncoder.encode(password);

        employee = Employee.builder()
            .loginId(dto.getLoginId())
            .password(encodePassword)
            .employeeNumber(employeeNumber)
            .name(dto.getName())
            .email(email)
            .phoneNumber(phoneNumber)
            .birthDate(dto.getBirthDate())
            .branchId(branch)
            .positionId(position)
            .authorityId(authority)
            .isApproved(IsApproved.PENDING)
            .status(Status.EMPLOYED)
            .build();

        employeeRepository.save(employee);

        EmployeeSignUpApproval employeeSignUpApproval = EmployeeSignUpApproval.builder()
            .employeeId(employee)
            .appliedAt(employee.getCreatedAt())
            .isApproved(employee.getIsApproved())
            .build();

        employeeSignUpApprovalRepository.save(employeeSignUpApproval);

//        Authority adminAuthority = authorityRepository.findByAuthorityName("Admin")
//            .orElseThrow(() -> new IllegalArgumentException(ResponseMessageKorean.USER_NOT_FOUND));
//
//        Employee finalEmployee = employee;

//        employeeRepository.findAll().stream()
//            .filter(emp -> emp.getAuthorityId().equals(adminAuthority))
//            .forEach(admin -> {
//                AlertCreateRequestDto alertCreateRequestDto = AlertCreateRequestDto.builder()
//                    .employeeId(admin.getEmployeeId())
//                    .alertType("SIGNUP_APPROVAL")
//                    .alertTargetTable("EMPLOYEES")
//                    .targetPk(finalEmployee.getEmployeeId())
//                    .message(finalEmployee.getName() + "님의 회원가입 승인 요청이 도착했습니다.")
//                    .build();
//
//                alertService.createAlert(alertCreateRequestDto);
//            });

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS);
    }

    @Override
    public ResponseDto<SignInResponseDto> login(SignInRequestDto dto) {
        SignInResponseDto responseDto = null;

        Employee employee = employeeRepository.findByLoginId(dto.getLoginId())
            .orElseThrow(() -> new UsernameNotFoundException("작원을 찾을 수 없습니다."));

        if (!bCryptPasswordEncoder.matches(dto.getPassword(), employee.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.generateToken(employee.getLoginId(), employee.getAuthorityId().getAuthorityName());
        int exprTime = jwtTokenProvider.getExpiration();

        EmployeeResponseDto response = EmployeeResponseDto.builder()
            .employeeId(employee.getEmployeeId())
            .employeeName(employee.getName())
            .employeeNumber(employee.getEmployeeNumber())
            .branchName(employee.getBranchId().getBranchName())
            .positionName(employee.getPositionId().getPositionName())
            .authorityName(employee.getAuthorityId().getAuthorityName())
            .build();

        responseDto = SignInResponseDto.builder()
            .token(token)
            .exprTime(exprTime)
            .employee(response)
            .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<Void> checkLoginIdDuplicate(String loginId) {
        if (employeeRepository.existsByLoginId(loginId)) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_USER_ID, ResponseMessageKorean.DUPLICATED_USER_ID);
        }

        return ResponseDto.success(ResponseCode.SUCCESS, "사용가능한 아이디입니다.");
    }

    @Override
    public ResponseDto<Void> logout(HttpServletResponse response) {
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", "")
            .path("/")
            .maxAge(0)
            .build();

        response.addHeader("Set-Cookie", accessTokenCookie.toString());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS);
    }
}
