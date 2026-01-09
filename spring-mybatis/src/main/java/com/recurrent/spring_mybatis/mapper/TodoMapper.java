package com.recurrent.spring_mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.recurrent.spring_mybatis.entity.Todo;

@Mapper  // MyBatisのMapperインターフェースであることを示すアノテーション
public interface TodoMapper {
  // ロール名でロールを取得
  public List<Todo> findAll();
  // タスクを終了させる
  public int deleteByIds(List<Integer> ids);
  // タスクを登録する
  public int insert(Todo todo);
}
