package com.berkedev.springbootblogapi.data.mapper;

import com.berkedev.springbootblogapi.data.dto.request.PostCreateRequest;
import com.berkedev.springbootblogapi.data.dto.request.PostUpdateRequest;
import com.berkedev.springbootblogapi.data.dto.response.PostResponse;
import com.berkedev.springbootblogapi.data.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final UserMapper userMapper;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;

    /// used mapper injections of other classes for data hiding of the object as wanted in dto's (user, etc...)
    public PostResponse toResponse(Post post) {
        if (post == null)
            return null;

        return PostResponse.builder()
                .id(post.getId())
                .slug(post.getSlug())
                .content(post.getContent())
                .title(post.getTitle())
                .published(post.isPublished())
                .publishedAt(post.getPublishedAt())
                .createdAt(post.getCreatedAt())

                .author(userMapper.toResponse(post.getAuthor()))
                .category(categoryMapper.toResponse(post.getCategory()))
                .tags(tagMapper.toResponseList(post.getTags()))
                .build();
    }

    /// author, category, tags will be set at service layer
    public Post toEntity(PostCreateRequest createRequest) {
        if (createRequest == null)
            return null;

        return Post.builder()
                .title(createRequest.getTitle())
                .slug(createRequest.getSlug())
                .content(createRequest.getContent())
                .published(createRequest.getPublished())
                .build();
    }

    /// category and tags will be handled at service layer
    public void updateEntityFromRequest(PostUpdateRequest updateRequest, Post post) {
        if (updateRequest == null || post == null )
            return;

        if (updateRequest.getTitle() != null) {
            post.setTitle(updateRequest.getTitle());
        }

        if (updateRequest.getSlug() != null) {
            post.setSlug(updateRequest.getSlug());
        }

        if (updateRequest.getContent() != null) {
            post.setContent(updateRequest.getContent());
        }

        if (updateRequest.getPublished() != null) {
            post.setPublished(updateRequest.getPublished());
        }
    }
}
