package com.berkedev.springbootblogapi.service.impl;

import com.berkedev.springbootblogapi.data.dto.request.CommentCreateRequest;
import com.berkedev.springbootblogapi.data.dto.response.CommentResponse;
import com.berkedev.springbootblogapi.data.dto.response.PostResponse;
import com.berkedev.springbootblogapi.data.entity.Comment;
import com.berkedev.springbootblogapi.data.entity.Post;
import com.berkedev.springbootblogapi.data.entity.User;
import com.berkedev.springbootblogapi.data.mapper.CommentMapper;
import com.berkedev.springbootblogapi.data.repository.CommentRepository;
import com.berkedev.springbootblogapi.data.repository.PostRepository;
import com.berkedev.springbootblogapi.data.repository.UserRepository;
import com.berkedev.springbootblogapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    // injections for relational entities
    // private final UserRepository userRepository; // will be implemented after spring security
    private final PostRepository postRepository;

    @Override
    public CommentResponse create(CommentCreateRequest createRequest) {
        // TODO: Implement after Spring Security
        // Will get current authenticated user from SecurityContext
        throw new UnsupportedOperationException("Comment creation requires authentication - Will be implemented with Spring Security");
    }

    @Override
    public CommentResponse getById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));

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
            throw new IllegalArgumentException("Comment not found with id: " + id);
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
