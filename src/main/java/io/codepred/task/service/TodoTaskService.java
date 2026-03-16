package io.codepred.task.service;

import io.codepred.task.dto.TodoTaskRequest;
import io.codepred.task.dto.TodoTaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for task business operations.
 * <p>
 * Defines the contract for task management operations including
 * creation, retrieval, update, and deletion of tasks.
 * Provides abstraction layer between controllers and data access.
 */
public interface TodoTaskService {
    /**
     * Creates a new task.
     *
     * @param todoTaskRequest the task data to create
     * @return the created task response
     */
    TodoTaskResponse createTodoTask(TodoTaskRequest todoTaskRequest);

    /**
     * Retrieves all tasks with pagination.
     *
     * @param pageable pagination and sorting parameters
     * @return paginated list of tasks
     */
    Page<TodoTaskResponse> getTodoTasks(Pageable pageable);

    /**
     * Retrieves a task by its identifier.
     *
     * @param id the unique identifier of the task
     * @return the task matching the provided id
     */
    TodoTaskResponse getTodoTask(Long id);

    /**
     * Updates an existing task.
     *
     * @param id the unique identifier of the task to update
     * @param todoTaskRequest the updated task data
     * @return the updated task response
     */
    TodoTaskResponse updateTodoTask(Long id, TodoTaskRequest todoTaskRequest);

    /**
     * Deletes a task by its identifier.
     *
     * @param id the unique identifier of the task to delete
     */
    void deleteTodoTask(Long id);
}
