package com.berkedev.springbootblogapi.controller;

import com.berkedev.springbootblogapi.data.dto.request.TagCreateRequest;
import com.berkedev.springbootblogapi.data.dto.response.TagResponse;
import com.berkedev.springbootblogapi.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.getById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<TagResponse> getByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.getByName(name));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TagResponse>> search(@RequestParam("keyword") String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.search(keyword));
    }

    @PostMapping
    public ResponseEntity<TagResponse> create(@Valid @RequestBody TagCreateRequest createRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.create(createRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
