package com.blogapi.mapper;

import com.blogapi.dto.response.CommentResponseDto;
import com.blogapi.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentResponseDto toResponse(Comment comment) {
        CommentResponseDto response = new CommentResponseDto();

        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setAuthorName(comment.getAuthor().getName());
        response.setPostId(comment.getPost().getId());

        return response;
    }
}
