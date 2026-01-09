package com.berkedev.springbootblogapi.service.impl;

import com.berkedev.springbootblogapi.data.dto.request.CommentCreateRequest;
import com.berkedev.springbootblogapi.data.dto.response.CommentResponse;
import com.berkedev.springbootblogapi.data.entity.Comment;
import com.berkedev.springbootblogapi.data.entity.Post;
import com.berkedev.springbootblogapi.data.entity.User;
import com.berkedev.springbootblogapi.data.mapper.CommentMapper;
import com.berkedev.springbootblogapi.data.repository.CommentRepository;
import com.berkedev.springbootblogapi.data.repository.PostRepository;
import com.berkedev.springbootblogapi.exception.ResourceNotFoundException;
import com.berkedev.springbootblogapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public CommentResponse create(CommentCreateRequest createRequest) {
        // Get current authenticated user from SecurityContext
        User author = getCurrentAuthenticatedUser();
        
        // Get post that comment belongs to
        Post post = postRepository.findById(createRequest.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", createRequest.getPostId()));
        
        // Create comment entity
        Comment comment = commentMapper.toEntity(createRequest);
        
        // Set author and post
        comment.setAuthor(author);
        comment.setPost(post);
        
        // Save comment
        Comment saved = commentRepository.save(comment);
        
        return commentMapper.toResponse(saved);
    }

    /**
     * Helper method to get current authenticated user from SecurityContext.
     * 
     * @return User current authenticated user
     */
    private User getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @Override
    public CommentResponse getById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

        return commentMapper.toResponse(comment);
    }

    @Override
    public List<CommentResponse> getAll() {
        return commentRepository.findAll()
                .stream()
                .map(commentMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comment", "id", id);
        }

        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentResponse> getByPostId(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(commentMapper::toResponse)
                .toList();
    }

    @Override
    public List<CommentResponse> getByAuthorId(Long authorId) {
        return commentRepository.findByAuthorId(authorId)
                .stream()
                .map(commentMapper::toResponse)
                .toList();
    }
}
