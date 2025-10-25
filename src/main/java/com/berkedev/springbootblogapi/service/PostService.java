package com.berkedev.springbootblogapi.service;

import com.berkedev.springbootblogapi.data.dto.request.PostCreateRequest;
import com.berkedev.springbootblogapi.data.dto.request.PostFilterRequest;
import com.berkedev.springbootblogapi.data.dto.request.PostUpdateRequest;
import com.berkedev.springbootblogapi.data.dto.response.PostResponse;

import java.util.List;

public interface PostService {

    PostResponse create(PostCreateRequest request);
    PostResponse getById(Long id);
    List<PostResponse> getAll(PostFilterRequest filterRequest);
    PostResponse update(Long id, PostUpdateRequest request);
    void delete(Long id);

    PostResponse publish(Long id);
    PostResponse unpublish(Long id);
}
