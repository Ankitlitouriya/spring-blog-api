package com.blogapi.service.impl;
import com.blogapi.dto.request.CommentRequestDto;
import com.blogapi.dto.response.CommentResponseDto;
import com.blogapi.entity.Comment;
import com.blogapi.entity.Post;
import com.blogapi.entity.User;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.mapper.CommentMapper;
import com.blogapi.repository.CommentRepository;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final CommentMapper commentMapper;


    //Create comment
    @Override
    public CommentResponseDto createComment(Long postId,
                                            CommentRequestDto request) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found"));

        Comment comment = new Comment();

        comment.setContent(request.getContent());
        comment.setAuthor(currentUser);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toResponse(savedComment);

    }
    //get All Comment
    @Override
    public List<CommentResponseDto> getAllComments(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found"));

        List<Comment> comments = commentRepository.findByPost(post);

        return comments.stream()
                .map( commentMapper::toResponse
                    ).toList();
    }

    //Get Comment by id
    @Override
    public CommentResponseDto getCommentById(Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Comment not found"));

        return  commentMapper.toResponse(comment);
    }


     //Update comment
    @Override
    public CommentResponseDto updateComment(Long id,
                                            CommentRequestDto request) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Comment not found"));

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        if (!comment.getAuthor().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    "You are not allowed to update this comment.");
        }

        comment.setContent(request.getContent());

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toResponse(savedComment);

    }

    @Override
    public void deleteComment(Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Comment not found"));

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        boolean isAdmin = currentUser.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (!comment.getAuthor().getId().equals(currentUser.getId())
                && !isAdmin) {

            throw new AccessDeniedException(
                    "You are not allowed to delete this comment.");
        }

        commentRepository.delete(comment);
    }
}