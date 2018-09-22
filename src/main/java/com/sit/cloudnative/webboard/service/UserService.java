package com.sit.cloudnative.webboard.service;

import com.sit.cloudnative.webboard.exception.ResourceNotFoundException;
import com.sit.cloudnative.webboard.model.User;
import com.sit.cloudnative.webboard.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  @Autowired
  private UserRepositoryInterface userRepositoryInterface;

  public User getUserById(Long userId){
    User user = userRepositoryInterface.getOne(userId);
    return user;
  }

  public List<User> getAllUser() {
    return userRepositoryInterface.findAll();
  }

  public User create(User user) {
    return userRepositoryInterface.save(user);
  }

  public User update(Long userId, User inbound) {
    return userRepositoryInterface.findById(userId).map(user -> {
      user.setFirstname(inbound.getFirstname());
      user.setLastname(inbound.getLastname());
      return userRepositoryInterface.save(user);
    }).orElseThrow(() ->
      new ResourceNotFoundException(String.format("User:id %d not found", userId))
    );
  }

  public Object delete(Long userId) {
    return userRepositoryInterface.findById(userId).map(user -> {
      userRepositoryInterface.delete(user);
      return ResponseEntity.ok().build();
    }).orElseThrow(() ->
      new ResourceNotFoundException(String.format("User:id %d not found", userId))
    );
  }
}
