package com.blogapi.mapper;

import com.blogapi.dto.response.PostResponseDto;
import com.blogapi.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostResponseDto toResponse(Post post){
        PostResponseDto dto = new PostResponseDto();

        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setAuthorName(post.getAuthor().getName());
        dto.setCategoryName(post.getCategory().getName());
        return dto;
    }
}
