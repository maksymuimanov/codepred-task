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

/**
 * Implementation of the {@link TodoTaskService} interface.
 * <p>
 * Provides the business logic for managing tasks, using
 * MapStruct for mapping and JPA for persistence.
 */
@Service
@RequiredArgsConstructor
public class TodoTaskServiceImpl implements TodoTaskService {
    private final TodoTaskMapper todoTaskMapper;
    private final TodoTaskRepository todoTaskRepository;

    /**
     * Creates a new task.
     *
     * @param todoTaskRequest the task data to create
     * @return the created task response
     */
    @Override
    @Transactional
    public TodoTaskResponse createTodoTask(TodoTaskRequest todoTaskRequest) {
        TodoTask todoTask = this.todoTaskMapper.toTodoTask(todoTaskRequest);
        this.todoTaskRepository.save(todoTask);
        return this.todoTaskMapper.toTodoTaskResponse(todoTask);
    }

    /**
     * Retrieves all tasks with pagination.
     *
     * @param pageable pagination and sorting parameters
     * @return paginated list of tasks
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TodoTaskResponse> getTodoTasks(Pageable pageable) {
        return this.todoTaskRepository.findAll(pageable)
                .map(this.todoTaskMapper::toTodoTaskResponse);
    }

    /**
     * Retrieves a task by its identifier.
     *
     * @param id the unique identifier of the task
     * @return the task matching the provided id
     * @throws TodoTaskNotFoundException if the task does not exist
     */
    @Override
    @Transactional(readOnly = true)
    public TodoTaskResponse getTodoTask(Long id) {
        TodoTask todoTask = this.todoTaskRepository.findById(id)
                .orElseThrow(TodoTaskNotFoundException::new);
        return this.todoTaskMapper.toTodoTaskResponse(todoTask);
    }

    /**
     * Updates an existing task.
     *
     * @param id the unique identifier of the task to update
     * @param todoTaskRequest the updated task data
     * @return the updated task response
     * @throws TodoTaskNotFoundException if the task does not exist
     */
    @Override
    @Transactional
    public TodoTaskResponse updateTodoTask(Long id, TodoTaskRequest todoTaskRequest) {
        TodoTask todoTask = this.todoTaskRepository.findById(id)
                .orElseThrow(TodoTaskNotFoundException::new);
        this.todoTaskMapper.updateTodoTask(todoTaskRequest, todoTask);
        this.todoTaskRepository.save(todoTask);
        return this.todoTaskMapper.toTodoTaskResponse(todoTask);
    }

    /**
     * Deletes a task by its identifier.
     *
     * @param id the unique identifier of the task to delete
     */
    @Override
    @Transactional
    public void deleteTodoTask(Long id) {
        this.todoTaskRepository.deleteById(id);
    }
}