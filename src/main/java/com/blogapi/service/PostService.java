package com.blogapi.service;

import com.blogapi.dto.request.PostRequestDto;
import com.blogapi.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;


import java.util.List;

public interface PostService {

    PostResponseDto createPost(PostRequestDto requestDto);

    Page<PostResponseDto> findAllPost(int page, int size,String sortBY, String direction);

    PostResponseDto findPostById(long id);
    PostResponseDto updatePost(long id,PostRequestDto requestDto);
     void deletePost(long id);

    Page<PostResponseDto> searchPosts(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction
    );
}
