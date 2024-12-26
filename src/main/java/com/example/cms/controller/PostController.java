package com.example.cms.controller;

import com.example.cms.dto.PostDTO;
import com.example.cms.model.Post;
import com.example.cms.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody Post post, Authentication authentication) {
        System.out.println("User from authentication: " + authentication.getName());
        Post savedPost = postService.createPost(post);
        PostDTO postDTO = mapToDTO(savedPost);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(post -> new ResponseEntity<>(mapToDTO(post), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping
    public ResponseEntity<Page<PostDTO>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postService.getAllPosts(pageable);
        Page<PostDTO> postDTOS = posts.map(this::mapToDTO);
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody Post updatedPost, Authentication authentication) {
        System.out.println("User from authentication: " + authentication.getName());
        try {
            Post updated = postService.updatePost(id, updatedPost);
            PostDTO postDTO = mapToDTO(updated);
            return new ResponseEntity<>(postDTO, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable Long id, Authentication authentication) {
        System.out.println("User from authentication: " + authentication.getName());
        try {
            postService.deletePost(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    private PostDTO mapToDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getSubtitle(),
                post.getContent(),
                post.getImageUrl()
        );
    }
}