package com.sit.cloudnative.webboard.controller;

import com.sit.cloudnative.webboard.service.UserService;
import com.sit.cloudnative.webboard.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  //GetList
  @RequestMapping(
          value = "",
          method = RequestMethod.GET
  )
  public ResponseEntity<List<User>> getUsers() {
    List<User> users = userService.getAllUser();
    return new ResponseEntity<List<User>>(users, HttpStatus.OK);
  }

  //GetByID
  @RequestMapping(
          value = "/{user_id:[\\d]}",
          method = RequestMethod.GET
  )
  public ResponseEntity<User> getUser(@PathVariable("user_id") Long id) {
    User user = userService.getUserById(id);
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  @RequestMapping(
          value = "",
          method = RequestMethod.POST
  )
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    return new ResponseEntity<User>(userService.create(user), HttpStatus.CREATED);
  }

  @RequestMapping(
          value = "/{user_id:[\\d]}",
          method = RequestMethod.PUT
  )
  public ResponseEntity<User> updateUser(@PathVariable("user_id") Long userId,
                                          @Valid @RequestBody User user) {
    return new ResponseEntity<User>(userService.update(userId, user), HttpStatus.OK);
  }

  @RequestMapping(
          value = "/{user_id:[\\d]}",
          method = RequestMethod.DELETE
  )
  public ResponseEntity<?> deleteUser(@PathVariable("user_id") Long userId) {
    return new ResponseEntity<Object>(userService.delete(userId), HttpStatus.OK);
  }
}
