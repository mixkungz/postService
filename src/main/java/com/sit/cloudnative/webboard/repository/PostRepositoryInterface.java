package com.sit.cloudnative.webboard.repository;

import com.sit.cloudnative.webboard.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface PostRepositoryInterface extends JpaRepository<Post, Long> {
}
