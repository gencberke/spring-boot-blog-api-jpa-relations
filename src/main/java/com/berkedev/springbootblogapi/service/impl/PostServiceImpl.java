package com.berkedev.springbootblogapi.service.impl;

import com.berkedev.springbootblogapi.data.dto.request.PostCreateRequest;
import com.berkedev.springbootblogapi.data.dto.request.PostFilterRequest;
import com.berkedev.springbootblogapi.data.dto.request.PostUpdateRequest;
import com.berkedev.springbootblogapi.data.dto.response.PostResponse;
import com.berkedev.springbootblogapi.data.entity.Category;
import com.berkedev.springbootblogapi.data.entity.Post;
import com.berkedev.springbootblogapi.data.entity.Tag;
import com.berkedev.springbootblogapi.data.mapper.PostMapper;
import com.berkedev.springbootblogapi.data.repository.CategoryRepository;
import com.berkedev.springbootblogapi.data.repository.PostRepository;
import com.berkedev.springbootblogapi.data.repository.TagRepository;
import com.berkedev.springbootblogapi.data.specification.PostSpecification;
import com.berkedev.springbootblogapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    // injections for related entities
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Override
    public PostResponse create(PostCreateRequest createRequest) {
        // TODO: Get authenticated user after spring security
        // User author = getCurrentAuthenticatedUser();

        if(postRepository.existsBySlug(createRequest.getSlug())) {
            throw new IllegalArgumentException("Slug already exists." + createRequest.getSlug());
        }

        Category category = categoryRepository.findById(createRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found."));

        List<Tag> tags = tagRepository.findAllById(createRequest.getTagIds());

        Post post = postMapper.toEntity(createRequest);

        post.setCategory(category);
        post.setTags(tags);

        if (post.isPublished()) {
            post.setPublishedAt(LocalDateTime.now());
        }

        Post saved = postRepository.save(post);

        return postMapper.toResponse(saved);
    }

    @Override
    public PostResponse getById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post with given id not found: " + id));

        return postMapper.toResponse(post);
    }

    @Override
    public List<PostResponse> getAll(PostFilterRequest filterRequest) {
        List<Post> posts;

        // If filter provided, use specification.
        if (filterRequest != null && filterRequest.hasFilters()) {
            Specification<Post> specification = PostSpecification.withFilters(filterRequest);
            posts = postRepository.findAll(specification);
        }

        // No filter, return all
        else {
            posts = postRepository.findAll();
        }

        return posts.stream()
                .map(postMapper::toResponse)
                .toList();
    }

    @Override
    public PostResponse update(Long id, PostUpdateRequest updateRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post with given id not found: " + id));

        if (updateRequest.getSlug() != null &&
            postRepository.existsBySlug(updateRequest.getSlug()) &&
            !post.getSlug().equals(updateRequest.getSlug())) {
            throw new RuntimeException("Slug already exists.");
        }

        if (updateRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(updateRequest.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with given id: " + id));
            post.setCategory(category);
        }

        if (updateRequest.getTagIds() != null) {
            List<Tag> tags = tagRepository.findAllById(updateRequest.getTagIds());
            post.setTags(tags);
        }

        postMapper.updateEntityFromRequest(updateRequest, post);

        if (updateRequest.getPublished() != null) {
            if (updateRequest.getPublished() && post.getPublishedAt() == null) {
                post.setPublishedAt(LocalDateTime.now());
            } else if (!updateRequest.getPublished()) {
                post.setPublishedAt(null);
            }
        }

        Post updated = postRepository.save(post);

        return postMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        postRepository.delete(postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post with given id not found: " + id)));
    }

    @Override
    public PostResponse publish(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post with given id not found: " + id));

        post.setPublished(true);
        post.setPublishedAt(LocalDateTime.now());

        Post saved = postRepository.save(post);
        return postMapper.toResponse(saved);
    }

    @Override
    public PostResponse unpublish(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post with given id not found: " + id));

        post.setPublished(false);
        post.setPublishedAt(null);

        Post saved = postRepository.save(post);
        return postMapper.toResponse(saved);
    }
}
