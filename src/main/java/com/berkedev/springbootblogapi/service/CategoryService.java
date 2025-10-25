package com.berkedev.springbootblogapi.service;

import com.berkedev.springbootblogapi.data.dto.request.CategoryCreateRequest;
import com.berkedev.springbootblogapi.data.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse create(CategoryCreateRequest createRequest);
    CategoryResponse getById(Long id);
    List<CategoryResponse> getAll();
    void delete(Long id);

    CategoryResponse getByName(String name);
    List<CategoryResponse> search(String keyword);
}
