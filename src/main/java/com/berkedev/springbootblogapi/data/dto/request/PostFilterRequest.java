package com.berkedev.springbootblogapi.data.dto.request;

import lombok.*;

/**
 * This DTO defines: What type of filters can apply to search.
 * Used in: PostSpecification.java
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostFilterRequest {
    private Long authorId;
    private Long categoryId;
    private Long tagId;
    private Boolean published;

    // helper method that checks if any filter applied
    public boolean hasFilters() {
        return authorId != null ||
                categoryId != null ||
                tagId != null ||
                published != null;
    }
}
