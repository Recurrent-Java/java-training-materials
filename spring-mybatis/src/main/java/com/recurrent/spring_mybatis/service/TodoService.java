package com.recurrent.spring_mybatis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recurrent.spring_mybatis.entity.Todo;
import com.recurrent.spring_mybatis.mapper.TodoMapper;

@Service
public class TodoService {
  private final TodoMapper todoMapper;

  public TodoService(TodoMapper todoMapper) {
    this.todoMapper = todoMapper;
  }

  // すべてのToDoを取得する
  public List<Todo> findAll() {
    return todoMapper.findAll();
  }

  // タスクを終了させる
  @Transactional
  public int deleteByIds(List<Integer> ids) {
    return todoMapper.deleteByIds(ids);
  }

  // タスクを登録する
  public Todo insert(String taskName) {
    if (taskName == null || taskName.trim().isEmpty()) {
      throw new IllegalArgumentException("タスク名が空です");
    }
    Todo todo = new Todo(null, taskName.trim(), false);
    int count = todoMapper.insert(todo);
    // 登録件数を返す場合は戻り値を返却
    System.out.println("登録件数: " + count);
    // Auto_Incrementで生成されたIDを参照したい場合は引数にセットしたTodoオブジェクトを返す
    return todo;
  }
}
