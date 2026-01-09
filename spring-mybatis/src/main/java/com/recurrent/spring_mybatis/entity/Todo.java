package com.recurrent.spring_mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // JPA利用時は、@Dataを避け、@Getter、@Setterを使用することが推奨される
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
  private Integer id;
  private String taskName;
  private Boolean completed;
}
