
package com.blogapi.service;

import com.blogapi.dto.request.CommentRequestDto;
import com.blogapi.dto.response.CommentResponseDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto createComment(Long postId,
                                     CommentRequestDto request);

    List<CommentResponseDto> getAllComments(Long postId);

    CommentResponseDto getCommentById(Long id);

    CommentResponseDto updateComment(Long id,
                                     CommentRequestDto request);

    void deleteComment(Long id);

}