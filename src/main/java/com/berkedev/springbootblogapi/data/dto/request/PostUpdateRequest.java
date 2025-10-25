package com.berkedev.springbootblogapi.data.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PostUpdateRequest {
    /**
     * On post update operations, updatable fields.
     * All fields nullable for partial update.
     */
    
    @Size(min = 5, max = 200, message = "Title must be between 5 and 200 characters")
    private String title;

    @Size(min = 5, max = 200, message = "Slug must be between 5 and 200 characters")
    private String slug;

    @Size(min = 50, message = "Content must be at least 50 characters")
    private String content;
    
    private Boolean published;  // Nullable - no default value for partial update
    
    private Long categoryId;
    
    private List<Long> tagIds;
}
