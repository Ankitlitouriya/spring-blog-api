package com.blogapi.controller;

import com.blogapi.dto.request.PostRequestDto;
import com.blogapi.dto.response.PostResponseDto;
import com.blogapi.service.AuthService;
import com.blogapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    //Create new post
    @PostMapping()
    public ResponseEntity<PostResponseDto>createPost(@RequestBody PostRequestDto requestDto){

        PostResponseDto postResponseDto = postService.createPost(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDto);
    }
   // Find all post
    @GetMapping()
    public ResponseEntity<List<PostResponseDto>>findAllPost(){
       List<PostResponseDto>postList = postService.findAllPost();

       return ResponseEntity.status(HttpStatus.OK)
               .body(postList);

    }
    //find post by post id
     @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto>findPostById(@PathVariable long id){
        PostResponseDto postResponseDto = postService.findPostById(id);

        return ResponseEntity.ok(postResponseDto);
    }
    //Update post
    @PutMapping("{id}")
    public ResponseEntity<PostResponseDto>updatePost(@PathVariable long id,
                                                     @RequestBody PostRequestDto request){
        PostResponseDto postResponseDto = postService.updatePost(id,request);

        return ResponseEntity.ok(postResponseDto);
    }
    //Delete Post
    @DeleteMapping("{id}")
    public ResponseEntity<?>deletePost(@PathVariable long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
