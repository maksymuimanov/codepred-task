package io.codepred.task.service;

import io.codepred.task.dto.TodoTaskRequest;
import io.codepred.task.dto.TodoTaskResponse;
import io.codepred.task.entity.TodoTask;
import io.codepred.task.exception.TodoTaskNotFoundException;
import io.codepred.task.mapper.TodoTaskMapper;
import io.codepred.task.repository.TodoTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoTaskServiceImpl implements TodoTaskService {
    private final TodoTaskMapper todoTaskMapper;
    private final TodoTaskRepository todoTaskRepository;

    @Override
    @Transactional
    public TodoTaskResponse createTodoTask(TodoTaskRequest todoTaskRequest) {
        TodoTask todoTask = this.todoTaskMapper.toTodoTask(todoTaskRequest);
        this.todoTaskRepository.save(todoTask);
        return this.todoTaskMapper.toTodoTaskResponse(todoTask);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TodoTaskResponse> getTodoTasks(Pageable pageable) {
        return this.todoTaskRepository.findAll(pageable)
                .map(this.todoTaskMapper::toTodoTaskResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public TodoTaskResponse getTodoTask(Long id) {
        TodoTask todoTask = this.todoTaskRepository.findById(id)
                .orElseThrow(TodoTaskNotFoundException::new);
        return this.todoTaskMapper.toTodoTaskResponse(todoTask);
    }

    @Override
    @Transactional
    public TodoTaskResponse updateTodoTask(Long id, TodoTaskRequest todoTaskRequest) {
        TodoTask todoTask = this.todoTaskMapper.toTodoTask(todoTaskRequest);
        todoTask.setId(id);
        this.todoTaskRepository.save(todoTask);
        return this.todoTaskMapper.toTodoTaskResponse(todoTask);
    }

    @Override
    @Transactional
    public void deleteTodoTask(Long id) {
        this.todoTaskRepository.deleteById(id);
    }
}