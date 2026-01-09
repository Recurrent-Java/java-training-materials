package com.recurrent.spring_mybatis.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data  // JPA利用時は、@Dataを避け、@Getter、@Setterを使用することが推奨される
@NoArgsConstructor  // 引数なしのコンストラクターをlombokが作成してくれる
@AllArgsConstructor // 全フィールドを使用したコンストラクターをlombokが作成してくれる
public class User {

  private Integer id;

  private String name;

  private String email;


  private String password;
  
  private Integer roleId;  // ★外部キー (登録用に使用)
  private Role role;       // ★関連オブジェクト(参照用に使用)

  private Boolean enabled;

  private Timestamp createdAt;

  private Timestamp updatedAt;
}
