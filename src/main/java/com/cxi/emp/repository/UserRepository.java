package com.cxi.emp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cxi.emp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
  Optional<User> findByVerificationToken(String token);
  Optional<User> findByEmail(String email);
}
