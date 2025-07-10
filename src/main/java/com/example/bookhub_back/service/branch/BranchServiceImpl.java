package com.example.bookhub_back.service.branch;

import com.example.bookhub_back.common.constants.ResponseCode;
import com.example.bookhub_back.common.constants.ResponseMessageKorean;
import com.example.bookhub_back.common.utils.DateUtils;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.branch.request.BranchCreateRequestDto;
import com.example.bookhub_back.dto.branch.request.BranchUpdateRequestDto;
import com.example.bookhub_back.dto.branch.response.BranchResponseDto;
import com.example.bookhub_back.entity.Branch;
import com.example.bookhub_back.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;

    @Override
    @Transactional
    public ResponseDto<Void> createBranch(BranchCreateRequestDto dto) {
        Branch branch = null;

        if (branchRepository.existsByBranchName(dto.getBranchName())) {
            throw new IllegalArgumentException("이미 존재하는 지점입니다.");
        }

        branch = Branch.builder()
            .branchName(dto.getBranchName())
            .branchLocation(dto.getBranchLocation())
            .build();

        branchRepository.save(branch);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS);
    }

    @Override
    public ResponseDto<List<BranchResponseDto>> getAllBranches() {
        List<BranchResponseDto> responseDtos = null;
        List<Branch> branches = null;

        branches = branchRepository.findAll();

        responseDtos = branches.stream()
            .map(branch -> BranchResponseDto.builder()
                .branchId(branch.getBranchId())
                .branchName(branch.getBranchName())
                .branchLocation(branch.getBranchLocation())
                .createdAt(DateUtils.format(branch.getCreatedAt()))
                .updatedAt(DateUtils.format(branch.getUpdatedAt()))
                .build())
            .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<List<BranchResponseDto>> getAllBranchesByLocation(String branchLocation) {
        List<BranchResponseDto> responseDtos = null;
        List<Branch> branches = null;

        branches = branchRepository.searchBranch(branchLocation);

        responseDtos = branches.stream()
            .map(branch -> BranchResponseDto.builder()
                .branchId(branch.getBranchId())
                .branchName(branch.getBranchName())
                .branchLocation(branch.getBranchLocation())
                .createdAt(DateUtils.format(branch.getCreatedAt()))
                .updatedAt(DateUtils.format(branch.getUpdatedAt()))
                .build())
            .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<BranchResponseDto> getBranchById(Long branchId) {
        return null;
    }

    @Override
    public ResponseDto<BranchResponseDto> updateBranch(Long branchId, BranchUpdateRequestDto dto) {
        return null;
    }
}
