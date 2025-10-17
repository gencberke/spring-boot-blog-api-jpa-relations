package com.berkedev.springbootblogapi.data.mapper;

import com.berkedev.springbootblogapi.data.dto.request.CommentCreateRequest;
import com.berkedev.springbootblogapi.data.dto.response.CommentResponse;
import com.berkedev.springbootblogapi.data.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserMapper userMapper;

    public CommentResponse toResponse(Comment comment) {
        if (comment == null)
            return null;

        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())

                /*
                  we used UserResponse at UserResponse dto so we need to convert user into
                  user response so that's why we injected and used userMapper here.
                 */

                .author(userMapper.toResponse(comment.getAuthor()))
                .build();
    }

    public Comment toEntity(CommentCreateRequest createRequest) {
        if (createRequest == null)
            return null;

        return new Comment(createRequest.getContent());
        // author and post will be set in service
    }
}