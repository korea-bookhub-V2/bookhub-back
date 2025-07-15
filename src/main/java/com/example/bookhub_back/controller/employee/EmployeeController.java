package com.example.bookhub_back.controller.employee;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.common.enums.Status;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.employee.response.EmployeeListResponseDto;
import com.example.bookhub_back.dto.employee.response.EmployeeResponseDto;
import com.example.bookhub_back.service.employee.EmployeeService;
import com.example.bookhub_back.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.ADMIN_API + "/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final MailService mailService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<EmployeeListResponseDto>>> searchEmployee(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Long branchId,
        @RequestParam(required = false) Long positionId,
        @RequestParam(required = false) Long authorityId,
        @RequestParam(required = false) Status status
    ) {
        ResponseDto<List<EmployeeListResponseDto>> responseDto = employeeService.searchEmployee(name, branchId, positionId, authorityId, status);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }
}
