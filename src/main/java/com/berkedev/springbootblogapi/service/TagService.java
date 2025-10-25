package com.berkedev.springbootblogapi.service;

import com.berkedev.springbootblogapi.data.dto.request.TagCreateRequest;
import com.berkedev.springbootblogapi.data.dto.response.TagResponse;

import java.util.List;

public interface TagService {

    TagResponse create(TagCreateRequest request);

    TagResponse getById(Long id);

    List<TagResponse> getAll();

    void delete(Long id);

    TagResponse getByName(String name);

    List<TagResponse> search(String keyword);

}
