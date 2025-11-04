package com.berkedev.springbootblogapi.service.impl;

import com.berkedev.springbootblogapi.data.dto.request.CategoryCreateRequest;
import com.berkedev.springbootblogapi.data.dto.response.CategoryResponse;
import com.berkedev.springbootblogapi.data.entity.Category;
import com.berkedev.springbootblogapi.data.mapper.CategoryMapper;
import com.berkedev.springbootblogapi.data.repository.CategoryRepository;
import com.berkedev.springbootblogapi.exception.DuplicateResourceException;
import com.berkedev.springbootblogapi.exception.ResourceNotFoundException;
import com.berkedev.springbootblogapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse create(CategoryCreateRequest createRequest) {
        if (categoryRepository.existsByName(createRequest.getName())) {
            throw new DuplicateResourceException("Category", createRequest.getName());
        }

        Category category = categoryMapper.toEntity(createRequest);
        Category saved = categoryRepository.save(category);

        return categoryMapper.toResponse(saved);
    }

    @Override
    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        return categoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category", "id", id);
        }

        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponse getByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name",  name));

        return categoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> search(String keyword) {
        return categoryRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }
}
