package com.example.bookhub_back.controller.branch;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.common.constants.ResponseCode;
import com.example.bookhub_back.common.constants.ResponseMessageKorean;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.branch.request.BranchCreateRequestDto;
import com.example.bookhub_back.dto.branch.request.BranchUpdateRequestDto;
import com.example.bookhub_back.dto.branch.response.BranchResponseDto;
import com.example.bookhub_back.service.branch.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.BASIC_API)
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;

    private static final String ADMIN_BRANCH ="/admin/branches";
    private static final String AUTH_BRANCH = "/auth/branches";

    @PostMapping(ADMIN_BRANCH)
    public ResponseEntity<ResponseDto<Void>> createBranch(@Valid @RequestBody BranchCreateRequestDto dto) {
        ResponseDto<Void> responseDto = branchService.createBranch(dto);
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, responseDto);
    }

    @GetMapping(AUTH_BRANCH)
    public ResponseEntity<ResponseDto<List<BranchResponseDto>>> getAllBranches() {
        ResponseDto<List<BranchResponseDto>> responseDto = branchService.getAllBranches();
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @GetMapping(ADMIN_BRANCH)
    public ResponseEntity<ResponseDto<List<BranchResponseDto>>> geAllBranchesByLocation(@RequestParam(required = false) String branchLocation) {
        ResponseDto<List<BranchResponseDto>> responseDto = branchService.getAllBranchesByLocation(branchLocation);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @GetMapping(ADMIN_BRANCH + "/{branchId}")
    public ResponseEntity<ResponseDto<BranchResponseDto>> getBranchById(@PathVariable Long branchId) {
        ResponseDto<BranchResponseDto> responseDto = branchService.getBranchById(branchId);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @PutMapping(ADMIN_BRANCH + "/{branchId}")
    public ResponseEntity<ResponseDto<BranchResponseDto>> updateBranch(@PathVariable Long branchId, @Valid @RequestBody BranchUpdateRequestDto dto) {
        ResponseDto<BranchResponseDto> responseDto = branchService.updateBranch(branchId, dto);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }
}
