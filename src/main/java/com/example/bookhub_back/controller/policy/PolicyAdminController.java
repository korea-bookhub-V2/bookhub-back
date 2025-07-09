//package com.example.bookhub_back.controller.policy;
//
//import com.example.bookhub_back.common.constants.ApiMappingPattern;
//import com.example.bookhub_back.dto.ResponseDto;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping(ApiMappingPattern.BASIC_API+ApiMappingPattern.ADMIN_API+"/policies")
//@RequiredArgsConstructor
//public class DiscountPolicyAdminController {
//
//    //서비스 단이랑 연결
//    private final PolicyService policyService;
//
//    //1) 할인 정책 생성
//    @PostMapping
//    public ResponseEntity<ResponseDto<Void>> createPolicy(
//            @Valid @RequestBody DiscountPolicyCreateRequestDto dto){
//        ResponseDto<Void> discountPolicy = discountPolicyService.createPolicy(dto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(discountPolicy);
//    }
//
//    //2) 할인 정책 수정
//    @PutMapping("/{policyId}")
//    public ResponseEntity<ResponseDto<Void>> updatePolicy(
//            @PathVariable Long policyId,
//            @Valid @RequestBody DiscountPolicyUpdateRequestDto dto){
//        ResponseDto<Void> responseDto = discountPolicyService.updatePolicy(policyId, dto);
//        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
//    }
//
//    //3) 할인 정책 삭제
//    @DeleteMapping("/{policyId}")
//    public ResponseEntity<ResponseDto<Void>> deletePolicy(@PathVariable Long policyId){
//        ResponseDto<Void> responseDto = discountPolicyService.deletePolicy(policyId);
//        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
//    }
//
//}