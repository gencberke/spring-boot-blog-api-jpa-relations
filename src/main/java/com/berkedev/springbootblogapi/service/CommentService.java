package com.berkedev.springbootblogapi.service;

import com.berkedev.springbootblogapi.data.dto.request.CommentCreateRequest;
import com.berkedev.springbootblogapi.data.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentResponse create(CommentCreateRequest request);

    CommentResponse getById(Long id);

    List<CommentResponse> getAll();

    void delete(Long id);

    List<CommentResponse> getByPostId(Long postId);      // A post's comments

    List<CommentResponse> getByAuthorId(Long authorId);  // A user's comments
}
