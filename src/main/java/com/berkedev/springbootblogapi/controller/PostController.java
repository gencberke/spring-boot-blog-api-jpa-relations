package com.berkedev.springbootblogapi.controller;

import com.berkedev.springbootblogapi.data.dto.request.PostCreateRequest;
import com.berkedev.springbootblogapi.data.dto.request.PostFilterRequest;
import com.berkedev.springbootblogapi.data.dto.request.PostUpdateRequest;
import com.berkedev.springbootblogapi.data.dto.response.PostResponse;
import com.berkedev.springbootblogapi.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // GET /api/posts?authorId=1&categoryId=2&tagId=3&published=true
    // RequestParam(required = false) -> client should be able to gather all Posts without any given filters if needed
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAll(
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long tagId,
            @RequestParam(required = false) Boolean published
    ) {
        PostFilterRequest filterRequest = PostFilterRequest.builder()
                .authorId(authorId)
                .categoryId(categoryId)
                .published(published)
                .tagId(tagId)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(postService.getAll(filterRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getById(id));
    }

    // TODO: After Spring Security
    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostCreateRequest createRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(createRequest));
    }

    // TODO: After Spring Security
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable Long id, @Valid @RequestBody PostUpdateRequest updateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.update(id, updateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}/publish")
    public ResponseEntity<PostResponse> publish(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.publish(id));
    }

    @PutMapping("/{id}/unpublish")
    public ResponseEntity<PostResponse> unpublish(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.unpublish(id));
    }
}