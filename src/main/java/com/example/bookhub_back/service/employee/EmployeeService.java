package com.example.bookhub_back.service.employee;

import com.example.bookhub_back.common.enums.Status;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.employee.response.EmployeeListResponseDto;

import java.util.List;

public interface EmployeeService {
    ResponseDto<List<EmployeeListResponseDto>> searchEmployee(String name, Long branchId, Long positionId, Long authorityId, Status status);
}
