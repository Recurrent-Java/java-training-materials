package com.recurrent.spring_mybatis.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recurrent.spring_mybatis.entity.Role;
import com.recurrent.spring_mybatis.entity.User;
import com.recurrent.spring_mybatis.form.SignupForm;
import com.recurrent.spring_mybatis.mapper.RoleMapper;
import com.recurrent.spring_mybatis.mapper.UserMapper;

@Service
public class UserService {
  private final UserMapper userMapper;
  private final RoleMapper roleMapper;
  private final PasswordEncoder passwordEncoder;
  // コンストラクターインジェクション
  public UserService(UserMapper userMapper, RoleMapper roleMapper, PasswordEncoder passwordEncoder) {
    this.userMapper = userMapper;
    this.roleMapper = roleMapper;
    this.passwordEncoder = passwordEncoder;
  }
  @Transactional
  public User createUser(SignupForm signupForm) {
      User user = new User();
      Role role = roleMapper.findByName("ROLE_GENERAL");

      user.setName(signupForm.getName());
      user.setEmail(signupForm.getEmail());
      user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
      user.setRoleId(role.getId());
      user.setEnabled(true);
      // 現在日時を取得
      LocalDateTime now = LocalDateTime.now();     
      user.setCreatedAt(Timestamp.valueOf(now));
      user.setUpdatedAt(Timestamp.valueOf(now));
      
      // ユーザーをデータベースに保存する
      int count = userMapper.insert(user);
      if (count != 1) {
        throw new IllegalStateException("ユーザー登録に失敗しました");
    }
      return user;
  }
  // メールアドレスが登録済みかどうかをチェックする
  public boolean isEmailRegistered(String email) {
      User user = userMapper.findByEmail(email);
      return user != null;
  }
  // パスワードとパスワード（確認用）の入力値が一致するかどうかをチェックする
  public boolean isSamePassword(String password, String passwordConfirmation) {
      return password.equals(passwordConfirmation);
  }
}
