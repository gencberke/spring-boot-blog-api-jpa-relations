package com.berkedev.springbootblogapi.controller;

import com.berkedev.springbootblogapi.data.dto.request.CategoryCreateRequest;
import com.berkedev.springbootblogapi.data.dto.response.CategoryResponse;
import com.berkedev.springbootblogapi.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Every method returns ResponseEntity. This means all methods give client HTTP status code that
     * describes whether the operation succeeded or failed
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryResponse> getByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getByName(name));
    }
    @GetMapping("/search")
    public ResponseEntity<List<CategoryResponse>> search(@RequestParam("keyword") String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.search(keyword));
    }

    /**
     * @Valid annotation starts validation operations on entity
     */
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryCreateRequest createRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(createRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
