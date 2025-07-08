package com.example.bookhub_back.service.impl;

import com.example.bookhub_back.common.constants.ResponseCode;
import com.example.bookhub_back.common.constants.ResponseMessageKorean;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.auth.request.SignInRequestDto;
import com.example.bookhub_back.dto.auth.response.SignInResponseDto;
import com.example.bookhub_back.dto.employee.response.EmployeeResponseDto;
import com.example.bookhub_back.entity.Employee;
import com.example.bookhub_back.provider.JwtTokenProvider;
import com.example.bookhub_back.repository.EmployeeRepository;
import com.example.bookhub_back.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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
}
