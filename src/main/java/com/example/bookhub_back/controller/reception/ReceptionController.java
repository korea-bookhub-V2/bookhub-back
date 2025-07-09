package com.example.bookhub_back.controller.reception;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.reception.response.ReceptionCreateResponseDto;
import com.example.bookhub_back.dto.reception.response.ReceptionListResponseDto;
import com.example.bookhub_back.service.reception.ReceptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.BASIC_API)
@RequiredArgsConstructor
public class ReceptionController {
    private final ReceptionService receptionService;

    @PostMapping(ApiMappingPattern.ADMIN_API + "/reception")
    public ResponseEntity<ResponseDto<ReceptionCreateResponseDto>> createReception(
            @RequestBody ReceptionCreateResponseDto dto,
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
    public ResponseEntity<ResponseDto<List<ReceptionListResponseDto>>> getPendingReceptions(
            @RequestHeader("Authorization") String token
    ) {
        ResponseDto<List<ReceptionListResponseDto>> reception = receptionService.getPendingList(token);
        return ResponseEntity.status(HttpStatus.OK).body(reception);
    }

    @GetMapping(ApiMappingPattern.MANAGER_API + "/reception/confirmed")
    public ResponseEntity<ResponseDto<List<ReceptionListResponseDto>>> getManagerConfirmedReceptions(
            @RequestHeader("Authorization") String token
    ) {
        ResponseDto<List<ReceptionListResponseDto>> reception = receptionService.getManagerConfirmedList(token);
        return ResponseEntity.status(HttpStatus.OK).body(reception);
    }

    @GetMapping(ApiMappingPattern.ADMIN_API + "/reception/logs")
    public ResponseEntity<ResponseDto<List<ReceptionListResponseDto>>> getAdminConfirmedReceptions(
            @RequestParam(required = false) String branchName,
            @RequestParam(value = "bookIsbn", required = false) String isbn
    ) {
        ResponseDto<List<ReceptionListResponseDto>> reception = receptionService.getAdminConfirmedList(branchName, isbn);
        return ResponseEntity.status(HttpStatus.OK).body(reception);
    }
}
