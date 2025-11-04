package com.berkedev.springbootblogapi.service.impl;

import com.berkedev.springbootblogapi.data.dto.request.TagCreateRequest;
import com.berkedev.springbootblogapi.data.dto.response.TagResponse;
import com.berkedev.springbootblogapi.data.entity.Tag;
import com.berkedev.springbootblogapi.data.mapper.TagMapper;
import com.berkedev.springbootblogapi.data.repository.TagRepository;
import com.berkedev.springbootblogapi.exception.DuplicateResourceException;
import com.berkedev.springbootblogapi.exception.ResourceNotFoundException;
import com.berkedev.springbootblogapi.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagResponse create(TagCreateRequest createRequest) {
        if (tagRepository.existsByName(createRequest.getName())) {
            throw new DuplicateResourceException("Tag", createRequest.getName());
        }

        Tag tag = tagMapper.toEntity(createRequest);
        Tag saved = tagRepository.save(tag);

        return tagMapper.toResponse(saved);
    }

    @Override
    public TagResponse getById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", id));

        return tagMapper.toResponse(tag);
    }

    @Override
    public List<TagResponse> getAll() {
        return tagRepository.findAll()
                .stream()
                .map(tagMapper::toResponse)
                .toList();

    }

    @Override
    public void delete(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tag","id", id);
        }

        tagRepository.deleteById(id);
    }

    @Override
    public TagResponse getByName(String name) {
        Tag tag = tagRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "name", name));

        return tagMapper.toResponse(tag);
    }

    @Override
    public List<TagResponse> search(String keyword) {
        List<Tag> tagList = tagRepository.findByNameContainingIgnoreCase(keyword);

        return tagMapper.toResponseList(tagList);
    }
}
