package com.recurrent.spring_mybatis.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.recurrent.spring_mybatis.entity.Todo;
import com.recurrent.spring_mybatis.service.TodoService;

@RestController
public class TodoApiController {
  private final TodoService todoService;

  public TodoApiController(TodoService todoService) {
    this.todoService = todoService;
  }

  @GetMapping("/api/todo")
  public List<Todo> todoApi() {
    return todoService.findAll();
  }

  @PostMapping("/api/todo/clear-completed")
  public Map<String, Object> clearCompleted(
      @RequestBody List<Integer> completedIds) {

    int deletedCount = todoService.deleteByIds(completedIds);

    System.out.println(completedIds);
    return Map.of("result", "OK");
  }

  @PostMapping("/api/todo/todo-task")
  public Todo todoTaskApi(@RequestBody String taskName) {

    Todo todo = todoService.insert(taskName);
    System.out.println(todo.getId());
    return todo;
  }
}
