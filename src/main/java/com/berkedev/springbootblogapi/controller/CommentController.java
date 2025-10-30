package com.berkedev.springbootblogapi.controller;

import com.berkedev.springbootblogapi.data.dto.request.CommentCreateRequest;
import com.berkedev.springbootblogapi.data.dto.response.CommentResponse;
import com.berkedev.springbootblogapi.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getById(id));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getByPostId(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getByPostId(postId));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<CommentResponse>> getByAuthorId(@PathVariable Long authorId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getByAuthorId(authorId));
    }

    // TODO: After security, with authorization.
    @PostMapping
    public ResponseEntity<CommentResponse> create(@Valid @RequestBody CommentCreateRequest createRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(createRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}