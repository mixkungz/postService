package com.sit.cloudnative.webboard.controller;

import com.sit.cloudnative.webboard.model.Post;
import com.sit.cloudnative.webboard.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

  @Autowired
  private PostService postService;

  @RequestMapping(
          value = "",
          method = RequestMethod.GET
  )
  public ResponseEntity<List<Post>> getPosts() {
    List<Post> posts = postService.getAllPost();
    return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
  }

  @RequestMapping(
          value = "/{post_id:[\\d]}",
          method = RequestMethod.GET
  )
  public ResponseEntity<Post> getPost(@PathVariable("post_id") long id) {
    Post post = postService.getPostById(id);
    return new ResponseEntity<Post>(post, HttpStatus.OK);
  }

  @RequestMapping(
          value = "",
          method = RequestMethod.POST
  )
  public ResponseEntity<Post> createPost(@RequestParam(value = "user_id", required = true) Long userId,
                                          @Valid @RequestBody Post post) {
    Post newPost = postService.create(userId, post);
    return new ResponseEntity<Post>(newPost, HttpStatus.CREATED);
  }

  @RequestMapping(
          value = "/{post_id:[\\d]}",
          method = RequestMethod.PUT
  )
  public ResponseEntity<Post> updatePost(@PathVariable("post_id") long id, @Valid @RequestBody Post post) {
    Post newPost = postService.update(id, post);
    return new ResponseEntity<Post>(newPost, HttpStatus.OK);
  }

  @RequestMapping(
          value = "/{post_id:[\\d]}",
          method = RequestMethod.DELETE
  )
  public ResponseEntity<Object> deletePost(@PathVariable("post_id") long postId) {
    Object post = postService.delete(postId);
    return new ResponseEntity<Object>(post, HttpStatus.OK);
  }


}
