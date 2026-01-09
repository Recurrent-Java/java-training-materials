package com.example.spring_auth_template.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring_auth_template.entity.Role;
import com.example.spring_auth_template.entity.User;
import com.example.spring_auth_template.form.SignupForm;
import com.example.spring_auth_template.repository.RoleRepository;
import com.example.spring_auth_template.repository.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
      this.userRepository = userRepository;
      this.roleRepository = roleRepository;
      this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public User createUser(SignupForm signupForm) {
      User user = new User();
      Role role = roleRepository.findByName("ROLE_GENERAL");

      user.setName(signupForm.getName());
      user.setEmail(signupForm.getEmail());
      user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
      user.setRole(role);
      user.setEnabled(true);

      return userRepository.save(user);
  }
  // メールアドレスが登録済みかどうかをチェックする
  public boolean isEmailRegistered(String email) {
      User user = userRepository.findByEmail(email);
      return user != null;
  }
  // パスワードとパスワード（確認用）の入力値が一致するかどうかをチェックする
  public boolean isSamePassword(String password, String passwordConfirmation) {
      return password.equals(passwordConfirmation);
  }
}
