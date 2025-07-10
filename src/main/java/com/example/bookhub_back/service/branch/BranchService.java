package com.example.bookhub_back.service.branch;

import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.branch.request.BranchCreateRequestDto;
import com.example.bookhub_back.dto.branch.request.BranchUpdateRequestDto;
import com.example.bookhub_back.dto.branch.response.BranchResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface BranchService {
    ResponseDto<Void> createBranch(@Valid BranchCreateRequestDto dto);

    ResponseDto<List<BranchResponseDto>> getAllBranches();

    ResponseDto<List<BranchResponseDto>> getAllBranchesByLocation(String branchLocation);

    ResponseDto<BranchResponseDto> getBranchById(Long branchId);

    ResponseDto<BranchResponseDto> updateBranch(Long branchId, @Valid BranchUpdateRequestDto dto);
}
