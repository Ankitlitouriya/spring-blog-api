package com.blogapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryRequest {
    @NotBlank(message = "Category name is required")
    @Size(max = 50,message = "Category name must not exceed 50 characters")
    private String name;
}
