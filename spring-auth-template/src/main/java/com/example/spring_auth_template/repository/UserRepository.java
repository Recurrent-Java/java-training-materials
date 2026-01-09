package com.example.spring_auth_template.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring_auth_template.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  public User findByEmail(String email);
}
