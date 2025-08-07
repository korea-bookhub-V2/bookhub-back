package com.example.bookhub_back.controller.location;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.PageResponseDto;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.location.response.LocationResponseDto;
import com.example.bookhub_back.service.location.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.COMMON_API+"/locations")
@RequiredArgsConstructor
@Tag(name = "Location API", description = "진열 위치 조회 API 입니다.")
public class LocationController {
    private final LocationService locationService;

    @Operation(summary = "진열 위치 조회", description = "진열 위치를 조건에 따라 조회합니다.")
    @GetMapping
    public ResponseEntity<ResponseDto<PageResponseDto<LocationResponseDto>>> getLocations(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long branchId) {
        ResponseDto<PageResponseDto<LocationResponseDto>> response = locationService.getFilteredLocations(page,size,keyword,branchId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "진열 위치 조회", description = "진열 위치 단건을 상세 조회합니다.")
    @GetMapping("/{locationId}")
    public ResponseEntity<ResponseDto<LocationResponseDto>> getLocationById(
            @PathVariable Long locationId
    ){
        ResponseDto<LocationResponseDto> location = locationService.getLocation(locationId);
        return ResponseEntity.status(HttpStatus.OK).body(location);
    }
}
