package com.blogapi.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {
   @NotBlank(message = "Title is required")
   @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
   private String title;
   @NotBlank(message = "content is required")
   private String content;
   @NotNull(message = "category is required")
   private Long categoryId;
}
