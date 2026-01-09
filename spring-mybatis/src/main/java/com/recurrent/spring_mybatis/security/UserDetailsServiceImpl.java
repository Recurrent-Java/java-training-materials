package com.recurrent.spring_mybatis.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.recurrent.spring_mybatis.entity.User;
import com.recurrent.spring_mybatis.mapper.UserMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserMapper userMapper;

  public UserDetailsServiceImpl(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    try {
      User user = userMapper.findByEmail(email);
      // ★ここが重要：null なら必ず UsernameNotFoundException を投げる
      if (user == null) {
        throw new UsernameNotFoundException("ユーザーが見つかりません: " + email);
      }
      // ユーザーのロール名を取得して GrantedAuthority に変換
      String userRoleName = user.getRole().getName();
      Collection<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority(userRoleName));

      return new UserDetailsImpl(user, authorities);
    } catch (Exception e) {
      e.printStackTrace();
      throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
    }
  }
}
