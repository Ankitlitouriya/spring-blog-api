package com.blogapi.service.impl;

import com.blogapi.dto.request.PostRequestDto;
import com.blogapi.dto.response.PostResponseDto;
import com.blogapi.entity.Category;
import com.blogapi.entity.Post;
import com.blogapi.entity.User;
import org.springframework.security.access.AccessDeniedException;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.mapper.PostMapper;
import com.blogapi.repository.CategoryRepository;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    private final PostMapper postMapper;

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
        Post savedPost = postRepository.save(post);

        return postMapper.toResponse(savedPost);


    }
    //find All post
    @Override
    public Page<PostResponseDto> findAllPost(int page, int size,String sortBy,String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> postPage = postRepository.findAll(pageable);

        return postPage.map(postMapper::toResponse);
    }

    //Find post by id

   public PostResponseDto findPostById(long id){
      Post post = postRepository.findById(id)
              .orElseThrow(()->new ResourceNotFoundException("post not found"));

       return postMapper.toResponse(post);
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

           return postMapper.toResponse(savedPost);
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
            throw new AccessDeniedException("you are not allowed to delete this post");
        }

        postRepository.deleteById(id);
    }
    @Override
    public Page<PostResponseDto> searchPosts(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction
    ){
        Sort sort = direction.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Post>postPage =
                postRepository.findByTitleContainingIgnoreCase(
                        keyword,
                        pageable
                );
            return postPage.map(postMapper::toResponse);

    }
}
