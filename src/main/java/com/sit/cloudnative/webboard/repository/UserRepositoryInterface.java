package com.sit.cloudnative.webboard.repository;

import com.sit.cloudnative.webboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryInterface extends JpaRepository<User, Long> {
}
