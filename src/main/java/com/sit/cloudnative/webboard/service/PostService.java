package com.sit.cloudnative.webboard.service;

import com.sit.cloudnative.webboard.exception.ResourceNotFoundException;
import com.sit.cloudnative.webboard.model.Post;
import com.sit.cloudnative.webboard.repository.PostRepositoryInterface;
import com.sit.cloudnative.webboard.repository.UserRepositoryInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.rmi.server.ExportException;
import java.util.List;

@Service
public class PostService {

  @Autowired
  private PostRepositoryInterface postRepositoryInterface;

  @Autowired
  private UserRepositoryInterface userRepositoryInterface;

  public List<Post> getAllPost() {
    return postRepositoryInterface.findAll();
  }

  public Post getPostById(long postId) {
    return postRepositoryInterface.getOne(postId);
  }

  public Post create(Long userId, Post post) {
    return userRepositoryInterface.findById(userId).map(user -> {
      post.setUser(user);
      return postRepositoryInterface.save(post);
    }).orElseThrow(() ->
      new ResourceNotFoundException(String.format("User:id %d not found", userId))
    );
  }

  public Post update(Long postId, Post inbound) {
    return postRepositoryInterface.findById(postId).map(post -> {
      post.setTitle(inbound.getTitle());
      post.setDescription(inbound.getDescription());
      return postRepositoryInterface.save(post);
    }).orElseThrow(() ->
      new ResourceNotFoundException(String.format("Post:id %d not found", postId))
    );
  }

  public Object delete(Long postId) {
    return postRepositoryInterface.findById(postId).map(post -> {
      postRepositoryInterface.delete(post);
      return ResponseEntity.ok().build();
    }).orElseThrow(() ->
      new ResourceNotFoundException(String.format("Post:id %d not found", postId))
    );
  }
}
