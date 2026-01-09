package com.example.java_extra_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.java_extra_spring.entity.UserServiceSetting;
import com.example.java_extra_spring.entity.id.ServiceId;

public interface ServiceRepository extends JpaRepository<UserServiceSetting, ServiceId> {

}
