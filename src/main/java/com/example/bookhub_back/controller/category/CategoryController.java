package com.example.bookhub_back.controller.category;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.common.enums.CategoryType;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.category.request.CategoryCreateRequestDto;
import com.example.bookhub_back.dto.category.request.CategoryUpdateRequestDto;
import com.example.bookhub_back.dto.category.response.CategoryCreateResponseDto;
import com.example.bookhub_back.dto.category.response.CategoryTreeResponseDto;
import com.example.bookhub_back.dto.category.response.CategoryUpdateResponseDto;
import com.example.bookhub_back.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Category API", description = "카테고리 관련 API입니다.")

public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "카테고리 등록", description = "카테고리를 등록합니다.")
    @PostMapping(ApiMappingPattern.ADMIN_API+"/categories")
    public ResponseEntity<ResponseDto<CategoryCreateResponseDto>> createCategory(
            @Valid @RequestBody CategoryCreateRequestDto dto) {
        ResponseDto<CategoryCreateResponseDto> category = categoryService.createCategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @Operation(summary = "카테고리 수정", description = "카테고리 정보를 수정합니다.")
    @PutMapping(ApiMappingPattern.ADMIN_API+"/categories/{categoryId}")
    public ResponseDto<CategoryUpdateResponseDto> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryUpdateRequestDto dto) {
        return categoryService.updateCategory(categoryId, dto);
    }

    @Operation(summary = "카테고리 삭제(비활성화)", description = "카테고리를 비활성화 합니다.")
    @DeleteMapping(ApiMappingPattern.ADMIN_API + "/categories/{categoryId}")
    public ResponseDto<Void> deleteCategory(@PathVariable Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }

    @Operation(summary = "트리형 카테고리 조회", description = "트리형 카테고리 구조를 전체 조회합니다.")
    @GetMapping(ApiMappingPattern.COMMON_API + "/categories/tree")
    public ResponseDto<List<CategoryTreeResponseDto>> getCategoryTree(@RequestParam CategoryType type) {
        return categoryService.getCategoryTree(type);
    }

    @Operation(summary = "활성 카테고리만 조회", description = "활성화된 카테고리만 조회합니다.")
    @GetMapping(ApiMappingPattern.COMMON_API + "/categories/active")
    public ResponseDto<List<CategoryTreeResponseDto>> getActiveCategories() {
        return categoryService.getAllActiveCategories();
    }

    @Operation(summary = "대분류 카테고리 조회", description = "대분류 카테고리를 조회합니다(Ex 소설)")
    @GetMapping(ApiMappingPattern.COMMON_API + "/categories/roots")
    public ResponseDto<List<CategoryTreeResponseDto>> getRootCategories() {
        return categoryService.getRootCategories();
    }

    @Operation(summary = "소분류 카테고리 조회", description = "대분류의 자식 카테고리(소분류)를 조회합니다.")
    @GetMapping(ApiMappingPattern.COMMON_API + "/categories/subcategories/{parentId}")
    public ResponseDto<List<CategoryTreeResponseDto>> getSubCategories(@PathVariable Long parentId) {
        return categoryService.getSubCategories(parentId);
    }

    @Operation(summary = "카테고리 할인 정책 조회", description = "해당 카테고리에 시행중인 할인 정책을 조회합니다.")
    @GetMapping(ApiMappingPattern.COMMON_API + "/categories/{categoryId}/policy")
    public ResponseDto<?> getPolicyByCategory(@PathVariable("categoryId") Long categoryId) {
        return categoryService.getPolicyByCategoryId(categoryId);
    }
}
