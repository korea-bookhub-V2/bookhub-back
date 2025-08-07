package com.example.bookhub_back.controller.policy;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.policy.request.PolicyCreateRequestDto;
import com.example.bookhub_back.dto.policy.request.PolicyUpdateRequestDto;
import com.example.bookhub_back.service.policy.PolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.ADMIN_API+"/policies")
@RequiredArgsConstructor
@Tag(name = "Policy Admin API", description = "정책 관리 API 입니다.")
public class PolicyAdminController {


    private final PolicyService policyService;

    @Operation(summary = "정책 생성", description = "정책을 생성합니다")
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createPolicy(
            @Valid @RequestBody PolicyCreateRequestDto dto){
        ResponseDto<Void> discountPolicy = policyService.createPolicy(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(discountPolicy);
    }


    @Operation(summary = "정책 수정", description = "정책을 수정합니다")
    @PutMapping("/{policyId}")
    public ResponseEntity<ResponseDto<Void>> updatePolicy(
            @PathVariable Long policyId,
            @Valid @RequestBody PolicyUpdateRequestDto dto){
        ResponseDto<Void> responseDto = policyService.updatePolicy(policyId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


    @Operation(summary = "정책 삭제", description = "정책을 삭제합니다")
    @DeleteMapping("/{policyId}")
    public ResponseEntity<ResponseDto<Void>> deletePolicy(@PathVariable Long policyId){
        ResponseDto<Void> responseDto = policyService.deletePolicy(policyId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}