package com.example.bookhub_back.controller.location;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.location.request.LocationCreateRequestDto;
import com.example.bookhub_back.dto.location.request.LocationUpdateRequestDto;
import com.example.bookhub_back.service.location.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.BASIC_API+ApiMappingPattern.MANAGER_API+"/branch/{branchId}/locations")
@RequiredArgsConstructor
public class LocationManagerController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createLocation(
            @PathVariable Long branchId,
            @RequestBody LocationCreateRequestDto dto) {
        ResponseDto<Void> location = locationService.createLocation(branchId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(location);
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<ResponseDto<Void>> updateLocation(
            @PathVariable Long branchId,
            @PathVariable Long locationId,
            @Valid @RequestBody LocationUpdateRequestDto dto){
        ResponseDto<Void> changeLocation = locationService.updateLocation(branchId,locationId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(changeLocation);
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<ResponseDto<Void>> deleteLocation(
            @PathVariable Long branchId,
            @PathVariable Long locationId){
        ResponseDto<Void> responseDto = locationService.deleteLocation(branchId, locationId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
