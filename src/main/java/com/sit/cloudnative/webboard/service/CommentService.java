package com.sit.cloudnative.webboard.service;

import com.sit.cloudnative.webboard.exception.ResourceNotFoundException;
import com.sit.cloudnative.webboard.model.Comment;
import com.sit.cloudnative.webboard.model.Post;
import com.sit.cloudnative.webboard.model.User;
import com.sit.cloudnative.webboard.repository.CommentRepositoryInterface;
import com.sit.cloudnative.webboard.repository.PostRepositoryInterface;
import com.sit.cloudnative.webboard.repository.UserRepositoryInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

  @Autowired
  private CommentRepositoryInterface commentRepositoryInterface;
  
  @Autowired
  private PostRepositoryInterface postRepositoryInterface;

  @Autowired
  private UserRepositoryInterface userRepositoryInterface;

  public Page<Comment> getAllCommentsByPostId(Long postId,
                                              Pageable pageable) {
    return commentRepositoryInterface.findByPostId(postId, pageable);
  }

  public Comment create(Long postId, Long userId, Comment comment) {
    Post post = postRepositoryInterface.getOne(postId);
    User user = userRepositoryInterface.getOne(userId);
    comment.setPost(post);
    comment.setUser(user);
    return commentRepositoryInterface.save(comment);
  }

  public Comment update(Long commentId, Comment inbound) {
    return commentRepositoryInterface.findById(commentId).map(comment -> {
      comment.setText(inbound.getText());
      return commentRepositoryInterface.save(comment);
    }).orElseThrow(() ->
      new ResourceNotFoundException(String.format("Comment:id %d not found", commentId))
    );
  }

  public Object delete(Long commentId) {
    return commentRepositoryInterface.findById(commentId).map(comment -> {
      commentRepositoryInterface.delete(comment);
      return ResponseEntity.ok().build();
    }).orElseThrow(() ->
      new ResourceNotFoundException(String.format("Comment:id %d not found", commentId))
    );
  }
}
