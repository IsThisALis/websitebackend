package com.isthisalis.website.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isthisalis.website.dto.CommentDTO;
import com.isthisalis.website.dto.PostDTO;

import com.isthisalis.website.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    
    /**
     * Post 
     */
    private final PostService postService;

    @GetMapping("/{postId}/comments")
    public List<CommentDTO> getComments(@PathVariable long postId) {
        return postService.getComments(postId);
    }

    @PostMapping("/{postId}/comments")
    public void addComment(@PathVariable long postId, @Valid @RequestBody CommentDTO comment) {
        postService.addComment(postId, comment);
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    public void editComment(@PathVariable long commentId, @Valid @RequestBody CommentDTO commentDTO) {
        postService.editComment(commentId, commentDTO);
    }


    @DeleteMapping("/{postId}/comments/{commentId}")
    public void deleteComment(@PathVariable long commentId) {
        postService.deleteComment(commentId);
    }


    @GetMapping
    public List<PostDTO> getPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public PostDTO getPost(@PathVariable long postId) {
        return postService.getPost(postId);
    }


    @DeleteMapping("/{postId}/delete")
    public void deletePost(@PathVariable long postId) {
        postService.deletePost(postId);
    }


    @PatchMapping("/{postId}")
    public void editPost(@PathVariable long postId, @Valid @RequestBody PostDTO postDTO) {
        postService.editPost(postId, postDTO);
    }

    @PostMapping
    public void addPost(@Valid @RequestBody PostDTO post) {
        postService.addPost(post);
    }
}
