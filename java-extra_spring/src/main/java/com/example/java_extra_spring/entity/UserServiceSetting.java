package com.example.java_extra_spring.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.example.java_extra_spring.entity.id.ServiceId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "service")
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceSetting {
  // 複合キーを設定する
  @EmbeddedId
  private ServiceId id;
}
