package com.sit.cloudnative.webboard.repository;

import com.sit.cloudnative.webboard.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepositoryInterface extends JpaRepository<Comment, Long> {
  Page<Comment> findByPostId(long postId, Pageable pageable);
}
