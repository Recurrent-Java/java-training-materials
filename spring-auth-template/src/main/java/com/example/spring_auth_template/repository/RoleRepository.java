package com.example.spring_auth_template.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring_auth_template.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
  public Role findByName(String name);
}
