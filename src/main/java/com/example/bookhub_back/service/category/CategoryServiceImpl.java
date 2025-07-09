package com.example.bookhub_back.service.category;

import com.example.bookhub_back.common.enums.CategoryType;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.category.request.CategoryCreateRequestDto;
import com.example.bookhub_back.dto.category.request.CategoryUpdateRequestDto;
import com.example.bookhub_back.dto.category.response.CategoryCreateResponseDto;
import com.example.bookhub_back.dto.category.response.CategoryTreeResponseDto;
import com.example.bookhub_back.dto.category.response.CategoryUpdateResponseDto;
import com.example.bookhub_back.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final PolicyRepository policyRepository;

    @Override
    public ResponseDto<CategoryCreateResponseDto> createCategory(CategoryCreateRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<CategoryUpdateResponseDto> updateCategory(Long categoryId, CategoryUpdateRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<Void> deleteCategory(Long categoryId) {
        return null;
    }

    @Override
    public ResponseDto<List<CategoryTreeResponseDto>> getCategoryTree(CategoryType type) {
        return null;
    }

    @Override
    public ResponseDto<List<CategoryTreeResponseDto>> getAllActiveCategories() {
        return null;
    }

    @Override
    public ResponseDto<List<CategoryTreeResponseDto>> getRootCategories() {
        return null;
    }

    @Override
    public ResponseDto<List<CategoryTreeResponseDto>> getSubCategories(Long parentId) {
        return null;
    }

    @Override
    public ResponseDto<?> getPolicyByCategoryId(Long categoryId) {
        return null;
    }
}
