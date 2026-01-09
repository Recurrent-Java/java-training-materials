package com.example.spring_auth_template.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.spring_auth_template.entity.User;
import com.example.spring_auth_template.repository.UserRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
      this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      try {
          User user = userRepository.findByEmail(email);
          // ★ここが重要：null なら必ず UsernameNotFoundException を投げる
          if (user == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりません: " + user);
          }
          String userRoleName = user.getRole().getName();
          Collection<GrantedAuthority> authorities = new ArrayList<>();
          authorities.add(new SimpleGrantedAuthority(userRoleName));
          return new UserDetailsImpl(user, authorities);
      } catch (Exception e) {
          throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
      }
  }
}
