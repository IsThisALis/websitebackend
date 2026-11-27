package com.isthisalis.website.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.isthisalis.website.dto.CommentDTO;
import com.isthisalis.website.dto.PostDTO;
import com.isthisalis.website.entity.Comment;
import com.isthisalis.website.entity.Post;
import com.isthisalis.website.repository.CommentRepository;
import com.isthisalis.website.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional 
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "posts")
    public List<PostDTO> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc()
            .stream()
            .map(PostDTO::wrap)
            .toList();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "post", key = "#id")
    public PostDTO getPost(long id) {
        return postRepository.findById(id)
            .map(PostDTO::wrap)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "comments", key = "#postId")
    public List<CommentDTO> getComments(long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
            .getComments()
            .stream()
            .map(CommentDTO::wrap)
            .toList();
    }

     
    @CacheEvict(value = "comments", key = "#postId")
    public void addComment(long postId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
        Comment comment = Comment.wrap(commentDTO);
        comment.setPost(post);
        post.getComments().add(comment);
        postRepository.save(post);  
    }


    @CacheEvict(value = "comments", allEntries = true)
    public void deleteComment(long commentId) {
        commentRepository.deleteById(commentId);
    }

    
    @CacheEvict(value = "comments", allEntries = true)
    public void editComment(long commentId, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
        comment.setAuthor(commentDTO.getAuthor());
        comment.setBody(commentDTO.getBody());
    }

     
    @Caching(evict = {
            @CacheEvict(value = "post", key = "#postId"),
            @CacheEvict(value = "posts", allEntries = true)}
    )
    public void deletePost(long postId) {
        postRepository.deleteById(postId);
    }

     
    @Caching(
        put = @CachePut(value = "post", key = "#postId"),
        evict = @CacheEvict(value = "posts", allEntries = true)
    )
    public PostDTO editPost(long postId, PostDTO postDTO) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));

        post.setContent(postDTO.getContent());
        post.setTitle(postDTO.getTitle());
        post.setImage(postDTO.getImage());
        return postDTO;
    }

     
    @CacheEvict(value = "posts", allEntries = true)
    public void addPost(PostDTO post) {
        postRepository.save(Post.wrap(post));
    }
}
