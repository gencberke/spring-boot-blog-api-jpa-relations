package com.berkedev.springbootblogapi.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostCreateRequest {
    /**
     * Only needed in post persist operations, fields below enough to create a new post
     */

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 200, message = "Title must be between 5 to 200 characters")
    private String title;

    @NotBlank(message = "Slug is required")
    @Size(min = 5, max = 200, message = "Slug must be between 5 to 200 characters")
    private String slug;

    @NotBlank(message = "Content is required")
    @Size(min = 50, message = "Content must be at least 50 characters")
    private String content;

    // Business rule: keep it in draft until user publishes it
    private Boolean published = false;

    /**
     * We will just take IDs of these entities from client and match them in service layer
     */
    @NotNull(message = "Category is required")
    private Long categoryId;
    
    private List<Long> tagIds = new ArrayList<>();
}
