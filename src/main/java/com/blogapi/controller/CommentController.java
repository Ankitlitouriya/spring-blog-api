package com.blogapi.controller;

import com.blogapi.dto.request.CommentRequestDto;
import com.blogapi.dto.response.CommentResponseDto;
import com.blogapi.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CommentController {

    private final CommentService commentService;

    // Create Comment
    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> createComment( @Valid
            @PathVariable Long postId,
            @RequestBody CommentRequestDto request) {

        CommentResponseDto response =
                commentService.createComment(postId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // Get All Comments of Post
    @GetMapping("/api/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getAllComments(
            @PathVariable Long postId) {

        return ResponseEntity.ok(
                commentService.getAllComments(postId)
        );
    }

    // Get Comment By Id
    @GetMapping("/api/comments/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                commentService.getCommentById(id)
        );
    }

    // Update Comment
    @PutMapping("/api/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@Valid
            @PathVariable Long id,
            @RequestBody CommentRequestDto request) {

        return ResponseEntity.ok(
                commentService.updateComment(id, request)
        );
    }

    // Delete Comment
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id) {

        commentService.deleteComment(id);

        return ResponseEntity.noContent().build();
    }

}