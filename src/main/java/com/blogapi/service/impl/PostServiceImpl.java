package com.blogapi.service.impl;

import com.blogapi.dto.request.PostRequestDto;
import com.blogapi.dto.response.CategoryResponse;
import com.blogapi.dto.response.PostResponseDto;
import com.blogapi.entity.Category;
import com.blogapi.entity.Post;
import com.blogapi.entity.User;
import com.blogapi.exception.AccessDeniedException;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.repository.CategoryRepository;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    //Create post
    @Override
    public PostResponseDto createPost(PostRequestDto requestDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        Post post = new Post();
        Category category = categoryRepository.findById(requestDto.getCategoryId()).
                orElseThrow(()->new ResourceNotFoundException("Category not found"));
        PostResponseDto postResponseDto = new PostResponseDto();

        post.setTitle(requestDto.getTitle());
        post.setAuthor(user);
        post.setCategory(category);
        post.setContent(requestDto.getContent());
        post.setCommentList(new ArrayList<>());
        postRepository.save(post);

        postResponseDto.setId(post.getId());
        postResponseDto.setContent(post.getContent());
        postResponseDto.setAuthorName(post.getAuthor().getName());
        postResponseDto.setCategoryName(post.getCategory().getName());
        postResponseDto.setTitle(post.getTitle());
        return postResponseDto;
    }
    //find All post
    @Override
    public List<PostResponseDto>findAllPost() {
        List<Post>postList = postRepository.findAll();

        return postList.stream().map(post -> {
            PostResponseDto postResponseDto = new PostResponseDto();
            postResponseDto.setId(post.getId());
            postResponseDto.setTitle(post.getTitle());
            postResponseDto.setContent(post.getContent());
            postResponseDto.setAuthorName(post.getAuthor().getName());
            postResponseDto.setCategoryName(post.getCategory().getName());
            return postResponseDto;
        }).toList();
    }

    //Find post by id

   public PostResponseDto findPostById(long id){
      Post post = postRepository.findById(id)
              .orElseThrow(()->new ResourceNotFoundException("post not found"));

      PostResponseDto postResponseDto = new PostResponseDto();

      postResponseDto.setId(post.getId());
      postResponseDto.setTitle(post.getTitle());
      postResponseDto.setContent(post.getContent());
      postResponseDto.setAuthorName(post.getAuthor().getName());
      postResponseDto.setCategoryName(post.getCategory().getName());
      return postResponseDto;
    }
   //Update post
    @Override
    public PostResponseDto updatePost(long id,PostRequestDto requestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Post does not exist"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        if (!post.getAuthor().getId().equals(currentUser.getId())){
                 throw new AccessDeniedException("You are not allowed to update this post.");
        }
        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category does not exist"));

        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());
        post.setCategory(category);

        Post savedPost = postRepository.save(post);

        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setId(savedPost.getId());
        postResponseDto.setTitle(savedPost.getTitle());
        postResponseDto.setContent(savedPost.getContent());
        postResponseDto.setAuthorName(savedPost.getAuthor().getName());
        postResponseDto.setCategoryName(savedPost.getCategory().getName());
        return postResponseDto;
    }
    //Delete Post
    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("post does not exist"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        boolean isAdmin  = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (!currentUser.getId().equals(post.getAuthor().getId()) && !isAdmin){
            throw new AccessDeniedException("you are not allowed to delet this post");
        }

        postRepository.deleteById(id);
    }
}
