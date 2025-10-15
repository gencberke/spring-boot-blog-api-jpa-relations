package com.berkedev.springbootblogapi.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String slug;
    private String content;
    private Boolean published;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;

    /**
     * we should return client response classes for other entities because: (examples from user)
     * data hiding -> if we did return User instead of UserResponse client will be able to view password info
     * to avoid endless loops -> if we did return User instead of UserResponse that user will implement a post-info which
     * is points this post. and this post point to that user and again and again...
     */

    private UserResponse author;
    private CategoryResponse category;
    private List<TagResponse> tags = new ArrayList<>();
}
