package com.example.bookhub_back.controller.location;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.location.request.LocationCreateRequestDto;
import com.example.bookhub_back.dto.location.request.LocationUpdateRequestDto;
import com.example.bookhub_back.security.auth.EmployeePrincipal;
import com.example.bookhub_back.service.location.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.MANAGER_API+"/locations")
@RequiredArgsConstructor
@Tag(name = "Location Manager API", description = "진열 위치 관리 API 입니다.")
public class LocationManagerController {

    private final LocationService locationService;

    @Operation(summary = "진열 위치 생성", description = "진열 위치를 생성합니다")
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createLocation(
            @RequestBody LocationCreateRequestDto dto,
            @AuthenticationPrincipal EmployeePrincipal employee) {
        Long branchId = employee.getBranchId();
        ResponseDto<Void> location = locationService.createLocation(branchId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(location);
    }

    @Operation(summary = "진열 위치 생성", description = "진열 위치를 수정합니다")
    @PutMapping("/{locationId}")
    public ResponseEntity<ResponseDto<Void>> updateLocation(
            @AuthenticationPrincipal EmployeePrincipal employee,
            @PathVariable Long locationId,
            @Valid @RequestBody LocationUpdateRequestDto dto){
        Long branchId = employee.getBranchId();
        ResponseDto<Void> changeLocation = locationService.updateLocation(branchId,locationId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(changeLocation);
    }

    @Operation(summary = "진열 위치 삭제", description = "진열 위치를 삭제합니다")
    @DeleteMapping("/{locationId}")
    public ResponseEntity<ResponseDto<Void>> deleteLocation(
            @AuthenticationPrincipal EmployeePrincipal employee,
            @PathVariable Long locationId){
        Long branchId = employee.getBranchId();
        ResponseDto<Void> responseDto = locationService.deleteLocation(branchId, locationId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
