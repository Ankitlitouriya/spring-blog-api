package com.blogapi.controller;

import com.blogapi.dto.request.PostRequestDto;
import com.blogapi.dto.response.PostResponseDto;
import com.blogapi.service.AuthService;
import com.blogapi.service.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
@SecurityRequirement(name = "bearerAuth")
public class PostController {

    private final PostService postService;
    //Create new post
    @PostMapping()
    public ResponseEntity<PostResponseDto>createPost(@Valid @RequestBody PostRequestDto requestDto){

        PostResponseDto postResponseDto = postService.createPost(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDto);
    }
   // Find all post
    @GetMapping()
    public ResponseEntity<?>findAllPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBY,
            @RequestParam(defaultValue = "asc") String direction)


    {
         //List<PostResponseDto>postList = postService.findAllPost();
        return ResponseEntity.ok(postService.findAllPost(page,size,sortBY,direction));

    }
    //find post by post id
     @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto>findPostById(@PathVariable long id){
        PostResponseDto postResponseDto = postService.findPostById(id);

        return ResponseEntity.ok(postResponseDto);
    }
    //Update post
    @PutMapping("{id}")
    public ResponseEntity<PostResponseDto>updatePost(@Valid @PathVariable long id,
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

    @GetMapping("/search")
    public ResponseEntity<Page<PostResponseDto>> searchPosts(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction) {

        return ResponseEntity.ok(
                postService.searchPosts(
                        keyword,
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }
}
