package com.example.java_extra_spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "member")
@NoArgsConstructor  // 引数なしのコンストラクターをlombokが作成してくれる
@AllArgsConstructor // 全フィールドを使用したコンストラクターをlombokが作成してくれる
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long member_id;

  @Column(name = "name")
  private String name;

  @Column(name = "age")
  private Integer age;

  @Column(name = "gender")
  private String gender;

  @Column(name = "role")
  private String role;

  @Column(name = "dm")
  private String dm;
}
