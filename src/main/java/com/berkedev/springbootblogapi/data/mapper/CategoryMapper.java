package com.berkedev.springbootblogapi.data.mapper;

import com.berkedev.springbootblogapi.data.dto.request.CategoryCreateRequest;
import com.berkedev.springbootblogapi.data.dto.response.CategoryResponse;
import com.berkedev.springbootblogapi.data.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public Category toEntity(CategoryCreateRequest createRequest) {
        if (createRequest == null) {
            return null;
        }

        return Category.builder()
                .name(createRequest.getName())
                .description(createRequest.getDescription())
                .build();
    }
}
