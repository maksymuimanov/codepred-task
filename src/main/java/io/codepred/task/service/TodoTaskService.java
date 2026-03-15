package io.codepred.task.service;

import io.codepred.task.dto.TodoTaskRequest;
import io.codepred.task.dto.TodoTaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoTaskService {
    TodoTaskResponse createTodoTask(TodoTaskRequest todoTaskRequest);

    Page<TodoTaskResponse> getTodoTasks(Pageable pageable);

    TodoTaskResponse getTodoTask(Long id);

    TodoTaskResponse updateTodoTask(Long id, TodoTaskRequest todoTaskRequest);

    void deleteTodoTask(Long id);
}
