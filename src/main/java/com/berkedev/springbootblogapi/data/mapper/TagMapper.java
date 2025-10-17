package com.berkedev.springbootblogapi.data.mapper;

import com.berkedev.springbootblogapi.data.dto.request.TagCreateRequest;
import com.berkedev.springbootblogapi.data.dto.response.TagResponse;
import com.berkedev.springbootblogapi.data.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagMapper {
    public TagResponse toResponse(Tag tag) {
        if (tag == null)
            return null;

        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    public List<TagResponse> toResponseList(List<Tag> tags) {
        List<TagResponse> tagResponsesList = new ArrayList<>();

        if (tags != null) {
            for (Tag tag : tags) {
                tagResponsesList.add(toResponse(tag));
            }
        }
        return tagResponsesList;
    }

    public Tag toEntity(TagCreateRequest createRequest) {
        if (createRequest == null)
            return null;

        return new Tag(createRequest.getName());
    }
}