package com.berkedev.springbootblogapi.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagCreateRequest {

    /**
     *  only needed in tag persist operations, fields below enough to create a new tag
     */

    @NotBlank(message = "Tag name required")
    @Size(min = 2, max = 15, message = "Tag name must be between 2 to 15")
    private String name;
}
