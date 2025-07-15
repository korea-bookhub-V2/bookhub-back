package com.example.bookhub_back.service.employee;

import com.example.bookhub_back.common.constants.ResponseCode;
import com.example.bookhub_back.common.constants.ResponseMessageKorean;
import com.example.bookhub_back.common.enums.IsApproved;
import com.example.bookhub_back.common.enums.Status;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.employee.response.EmployeeListResponseDto;
import com.example.bookhub_back.entity.Employee;
import com.example.bookhub_back.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    @Override
    public ResponseDto<List<EmployeeListResponseDto>> searchEmployee(String name, Long branchId, Long positionId, Long authorityId, Status status) {
        List<Employee> employees = employeeRepository.searchEmployee(name, branchId, positionId, authorityId, status);

        List<EmployeeListResponseDto> responseDtos = employees.stream()
            .filter(employee -> employee.getIsApproved() == IsApproved.APPROVED)
            .map(employee -> EmployeeListResponseDto.builder()
                .employeeId(employee.getEmployeeId())
                .employeeNumber(employee.getEmployeeNumber())
                .employeeName(employee.getName())
                .branchName(employee.getBranchId().getBranchName())
                .positionName(employee.getPositionId().getPositionName())
                .authorityName(employee.getAuthorityId().getAuthorityName())
                .status(employee.getStatus())
                .build())
            .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, responseDtos);
    }
}
