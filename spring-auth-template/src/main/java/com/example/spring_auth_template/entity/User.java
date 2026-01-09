package com.example.spring_auth_template.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Setter(AccessLevel.PRIVATE) // IDはシステムが管理するため、外部からの直接書き換えを禁止
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  /**
   * パスワードの保護
   * 1. @ToString.Exclude: ログ出力時にハッシュ値が漏洩するのを防ぐ
   * 2. @JsonProperty: JSONレスポンス（API出力）には含めず、入力時のみ受け付ける
   */
  @ToString.Exclude
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Column(name = "password", nullable = false)
  private String password;

  @ManyToOne
  @JoinColumn(name = "role_id")
  private Role role;

  @Column(name = "enabled")
  private Boolean enabled;

  /**
   * 日付管理 (LocalDateTime)
   * SetterをPRIVATEにし、リクエストボディからの直接上書きを禁止する
   */
  @Column(name = "created_at", updatable = false)
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime updatedAt;

  // --- ライフサイクルコールバック (プログラムによる自動設定) ---

  /**
   * 新規保存時に実行
   */
  @PrePersist
  public void onPrePersist() {
    LocalDateTime now = LocalDateTime.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  /**
   * 更新時に実行
   */
  @PreUpdate
  public void onPreUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}
