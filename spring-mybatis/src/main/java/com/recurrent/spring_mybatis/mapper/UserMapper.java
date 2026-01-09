package com.recurrent.spring_mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.recurrent.spring_mybatis.entity.User;

@Mapper  // MyBatisのMapperインターフェースであることを示すアノテーション
public interface UserMapper {
    
  // メールアドレスでユーザーを検索（Roleも含む）
  User findByEmail(String email);
  
  // IDでユーザーを検索（Roleも含む）
  User findById(Integer id);
  
  // ユーザー名でユーザーを検索
  User findByName(String name);
  
  // 全ユーザーを取得（Roleも含む）
  List<User> findAll();
  
  // ユーザーを新規登録
  int insert(User user);
  
  // ユーザー情報を更新
  int update(User user);
  
  // ユーザーを削除
  int delete(Integer id);
  
  // メールアドレスの重複チェック
  int countByEmail(String email);

}
