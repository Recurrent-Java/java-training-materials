package com.example.java_extra_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.java_extra_spring.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
