package com.berkedev.springbootblogapi.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreateRequest {
    @NotBlank(message = "Content is required")
    @Size(min = 5, max = 1000)
    private String content;

    @NotNull(message = "Post Id is required")
    private Long postId;
}
