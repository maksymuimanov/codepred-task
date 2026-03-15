package io.codepred.task.controller;

import io.codepred.task.dto.TodoTaskRequest;
import io.codepred.task.dto.TodoTaskResponse;
import io.codepred.task.service.TodoTaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/{version}/tasks", version = "1.0")
@RequiredArgsConstructor
public class TodoTaskController {
    private final TodoTaskService todoTaskService;

    @PostMapping
    public ResponseEntity<TodoTaskResponse> postTodoTask(@RequestBody @Valid TodoTaskRequest todoTaskRequest) {
        TodoTaskResponse todoTaskResponse = this.todoTaskService.createTodoTask(todoTaskRequest);
        return ResponseEntity.ok(todoTaskResponse);
    }

    @GetMapping
    public PagedModel<TodoTaskResponse> getTodoTasks(Pageable pageable) {
        Page<TodoTaskResponse> todoTaskResponses = this.todoTaskService.getTodoTasks(pageable);
        return new PagedModel<>(todoTaskResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoTaskResponse> getTodoTaskById(@PathVariable Long id) {
        TodoTaskResponse todoTaskResponse = this.todoTaskService.getTodoTask(id);
        return ResponseEntity.ok(todoTaskResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoTaskResponse> putTodoTask(@PathVariable Long id, @RequestBody @Valid TodoTaskRequest todoTaskRequest) {
        TodoTaskResponse todoTaskResponse = this.todoTaskService.updateTodoTask(id, todoTaskRequest);
        return ResponseEntity.ok(todoTaskResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoTask(@PathVariable Long id) {
        this.todoTaskService.deleteTodoTask(id);
        return ResponseEntity.ok().build();
    }
}
