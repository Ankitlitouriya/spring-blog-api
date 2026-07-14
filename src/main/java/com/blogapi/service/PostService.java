package com.blogapi.service;

import com.blogapi.dto.request.PostRequestDto;
import com.blogapi.dto.response.PostResponseDto;

import java.util.List;

public interface PostService {

    PostResponseDto createPost(PostRequestDto requestDto);

    List<PostResponseDto> findAllPost();

    PostResponseDto findPostById(long id);
    PostResponseDto updatePost(long id,PostRequestDto requestDto);
     void deletePost(long id);
}
