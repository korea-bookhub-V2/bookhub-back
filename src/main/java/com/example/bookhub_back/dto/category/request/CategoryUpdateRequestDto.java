package com.example.bookhub_back.dto.category.request;

import com.example.bookhub_back.common.enums.CategoryType;
import com.example.bookhub_back.entity.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryUpdateRequestDto {

    private Category parentCategoryId;

    @NotBlank(message = "카테고리명은 필수 입력 값입니다.")
    private String categoryName;

    @NotBlank(message = "카테고리 레벨은 필수 입력 값입니다.")
    private int categoryLevel;

    @NotBlank(message = "카테고리 타입은 필수 입력 값입니다.")
    private CategoryType categoryType;

    private int categoryOrder;

    private Boolean isActive;

    private Long discountPolicyId;
}
