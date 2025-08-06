package com.example.bookhub_back.controller.branch;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.PageResponseDto;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.branch.request.BranchCreateRequestDto;
import com.example.bookhub_back.dto.branch.request.BranchUpdateRequestDto;
import com.example.bookhub_back.dto.branch.response.BranchResponseDto;
import com.example.bookhub_back.service.branch.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.BASIC_API)
@RequiredArgsConstructor
@Tag(name = "Branch API", description = "지점 관련 API입니다.")
public class BranchController {
    private final BranchService branchService;

    private static final String ADMIN_BRANCH ="/admin/branches";
    private static final String AUTH_BRANCH = "/auth/branches";

    @Operation(summary = "지점 등록", description = "새로운 지점을 등록합니다.")
    @PostMapping(ADMIN_BRANCH)
    public ResponseEntity<ResponseDto<Void>> createBranch(@Valid @RequestBody BranchCreateRequestDto dto) {
        ResponseDto<Void> responseDto = branchService.createBranch(dto);
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, responseDto);
    }

    @Operation(summary = "지점 전체 조회", description = "전체 지점 목록을 조회합니다.")
    @GetMapping(AUTH_BRANCH)
    public ResponseEntity<ResponseDto<List<BranchResponseDto>>> getAllBranches() {
        ResponseDto<List<BranchResponseDto>> responseDto = branchService.getAllBranches();
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @Operation(summary = "지점 검색 조회", description = "지점위치로 지점을 조회합니다.")
    @GetMapping(ADMIN_BRANCH)
    public ResponseEntity<ResponseDto<PageResponseDto<BranchResponseDto>>> getAllBranchesByLocation(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "10") @Min(1) int size,
        @RequestParam(required = false) String branchLocation) {
        ResponseDto<PageResponseDto<BranchResponseDto>> responseDto = branchService.getAllBranchesByLocation(page, size, branchLocation);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @Operation(summary = "지점 상세 조회", description = "지점 ID로 지점을 조회합니다.")
    @GetMapping(ADMIN_BRANCH + "/{branchId}")
    public ResponseEntity<ResponseDto<BranchResponseDto>> getBranchById(@PathVariable Long branchId) {
        ResponseDto<BranchResponseDto> responseDto = branchService.getBranchById(branchId);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @Operation(summary = "지점 정보 수정", description = "지점 ID로 지점 정보를 수정합니다.")
    @PutMapping(ADMIN_BRANCH + "/{branchId}")
    public ResponseEntity<ResponseDto<BranchResponseDto>> updateBranch(@PathVariable Long branchId, @Valid @RequestBody BranchUpdateRequestDto dto) {
        ResponseDto<BranchResponseDto> responseDto = branchService.updateBranch(branchId, dto);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }
}
