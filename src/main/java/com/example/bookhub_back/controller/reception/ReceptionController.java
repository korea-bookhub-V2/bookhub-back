package com.example.bookhub_back.controller.reception;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.PageResponseDto;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.reception.request.ReceptionCreateRequestDto;
import com.example.bookhub_back.dto.reception.response.ReceptionCreateResponseDto;
import com.example.bookhub_back.dto.reception.response.ReceptionListResponseDto;
import com.example.bookhub_back.service.reception.ReceptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.BASIC_API)
@RequiredArgsConstructor
public class ReceptionController {
    private final ReceptionService receptionService;

    @PostMapping(ApiMappingPattern.ADMIN_API + "/reception")
    public ResponseEntity<ResponseDto<ReceptionCreateResponseDto>> createReception(
            @RequestBody ReceptionCreateRequestDto dto,
            @RequestHeader("Authorization") String token
    ) {
        ResponseDto<ReceptionCreateResponseDto> reception = receptionService.createReception(dto, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(reception);
    }

    @PutMapping(ApiMappingPattern.MANAGER_API + "/reception/approve/{id}")
    public ResponseEntity<ResponseDto<Void>> approveReception(
            @PathVariable Long id, @RequestHeader("Authorization") String token
    ) {
        ResponseDto<Void> responseDto = receptionService.approveReception(id, token);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping(ApiMappingPattern.MANAGER_API + "/reception/pending")
    public ResponseEntity<ResponseDto<PageResponseDto<ReceptionListResponseDto>>> getPendingReceptions(
            @RequestHeader("Authorization") String token,
            @RequestParam int page,
            @RequestParam int size
    ) {
        ResponseDto<PageResponseDto<ReceptionListResponseDto>> reception = receptionService.getPendingList(token, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(reception);
    }

    @GetMapping(ApiMappingPattern.MANAGER_API + "/reception/confirmed")
    public ResponseEntity<ResponseDto<PageResponseDto<ReceptionListResponseDto>>> getManagerConfirmedReceptions(
            @RequestHeader("Authorization") String token,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        ResponseDto<PageResponseDto<ReceptionListResponseDto>> reception = receptionService.getManagerConfirmedList(token, page, size, startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK).body(reception);
    }

    @GetMapping(ApiMappingPattern.ADMIN_API + "/reception/logs")
    public ResponseEntity<ResponseDto<PageResponseDto<ReceptionListResponseDto>>> getAdminConfirmedReceptions(
            @RequestParam(required = false) String branchName,
            @RequestParam(value = "bookIsbn", required = false) String isbn,
            @RequestParam int page,
            @RequestParam int size
    ) {
        ResponseDto<PageResponseDto<ReceptionListResponseDto>> reception = receptionService.getAdminConfirmedList(branchName, isbn, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(reception);
    }
}
