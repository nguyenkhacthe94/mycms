package com.example.cms.service;

import com.example.cms.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostService {
    Post createPost(Post post);

    Optional<Post> getPostById(Long id);

    Page<Post> getAllPosts(Pageable pageable);

    Post updatePost(Long id, Post updatedPost);

    void deletePost(Long id);
}