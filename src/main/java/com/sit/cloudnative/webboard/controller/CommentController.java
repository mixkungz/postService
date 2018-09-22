package com.sit.cloudnative.webboard.controller;

import com.sit.cloudnative.webboard.model.Comment;
import com.sit.cloudnative.webboard.model.Post;
import com.sit.cloudnative.webboard.service.CommentService;
import com.sit.cloudnative.webboard.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/posts")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @RequestMapping(
          value = "/{post_id:[\\d]}/comments",
          method = RequestMethod.GET
  )
  public ResponseEntity<Page<Comment>> getAllCommentsByPostId(@PathVariable(value = "post_id") Long postId,
                                                              Pageable pageable) {
    Page<Comment> comments = commentService.getAllCommentsByPostId(postId, pageable);
    return new ResponseEntity<Page<Comment>>(comments, HttpStatus.OK);
  }


  @RequestMapping(
          value = "/{post_id}/comments",
          method = RequestMethod.POST
  )
  public ResponseEntity<Comment> createComment(@PathVariable (value = "post_id") long postId,
                                               @RequestParam(value = "user_id", required = true) Long userId,
                                               @Valid @RequestBody Comment comment) {
    commentService.create(postId, userId, comment);
    return new ResponseEntity<Comment>(comment, HttpStatus.CREATED);
  }

  @RequestMapping(
          value = "/{post_id}/comments/{comment_id}",
          method = RequestMethod.PUT
  )
  public ResponseEntity<Comment> updateComment(@PathVariable (value = "comment_id") long commentId,
                                               @Valid @RequestBody Comment comment) {
    return new ResponseEntity<Comment>(commentService.update(commentId, comment), HttpStatus.OK);
  }

  @RequestMapping(
          value = "/{post_id}/comments/{comment_id}",
          method = RequestMethod.DELETE
  )
  public ResponseEntity<?> deleteComment(@PathVariable (value = "comment_id") long commentId) {
    return new ResponseEntity<Object>(commentService.delete(commentId), HttpStatus.OK);
  }
}
