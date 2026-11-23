package com.blogapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    @NotBlank(message = "Comment cannot be empty")
    @Size(max = 500, message = "Comment must not exceed 500 characters")
    private String content;
}
