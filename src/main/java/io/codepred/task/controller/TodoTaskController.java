package io.codepred.task.controller;

import io.codepred.task.dto.TodoTaskRequest;
import io.codepred.task.dto.TodoTaskResponse;
import io.codepred.task.service.TodoTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.boot.webmvc.error.ErrorAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing tasks.
 * <p>
 * Exposes CRUD operations for tasks through REST API endpoints.
 * Handles HTTP requests, delegates business logic to services,
 * and returns JSON responses to API clients.
 */
@RestController
@RequestMapping(path = "/api/{version}/tasks", version = "1.0")
@Tag(name = "Todo Tasks", description = "Todo tasks management")
@RequiredArgsConstructor
public class TodoTaskController {
    private final TodoTaskService todoTaskService;

    /**
     * Creates a new task.
     *
     * @param todoTaskRequest the task data to create
     * @return the created task response
     */
    @PostMapping
    @Operation(summary = "Create a new todo task", description = "Creates and saves a new todo task to the database")
    @ApiResponse(responseCode = "200", description = "Todo task created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorAttributes.class)))
    public ResponseEntity<TodoTaskResponse> postTodoTask(@RequestBody @Valid TodoTaskRequest todoTaskRequest) {
        TodoTaskResponse todoTaskResponse = this.todoTaskService.createTodoTask(todoTaskRequest);
        return ResponseEntity.ok(todoTaskResponse);
    }

    /**
     * Retrieves all tasks with pagination.
     *
     * @param pageable pagination and sorting parameters
     * @return paginated list of tasks
     */
    @GetMapping
    @Operation(summary = "Get todo tasks", description = "Retrieves a page of all todo tasks")
    @ApiResponse(responseCode = "200", description = "List of todo tasks retrieved successfully")
    public PagedModel<TodoTaskResponse> getTodoTasks(@ParameterObject Pageable pageable) {
        Page<TodoTaskResponse> todoTaskResponses = this.todoTaskService.getTodoTasks(pageable);
        return new PagedModel<>(todoTaskResponses);
    }

    /**
     * Retrieves a task by its identifier.
     *
     * @param id the unique identifier of the task
     * @return the task matching the provided id
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get todo task by id", description = "Retrieves a todo task by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Todo task retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Todo task not found", content = @Content(schema = @Schema(implementation = ErrorAttributes.class)))
    public ResponseEntity<TodoTaskResponse> getTodoTaskById(@PathVariable Long id) {
        TodoTaskResponse todoTaskResponse = this.todoTaskService.getTodoTask(id);
        return ResponseEntity.ok(todoTaskResponse);
    }

    /**
     * Updates an existing task.
     *
     * @param id the unique identifier of the task to update
     * @param todoTaskRequest the updated task data
     * @return the updated task response
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update todo task by id", description = "Updates a todo task by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Todo task updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorAttributes.class)))
    public ResponseEntity<TodoTaskResponse> putTodoTask(@PathVariable Long id, @RequestBody @Valid TodoTaskRequest todoTaskRequest) {
        TodoTaskResponse todoTaskResponse = this.todoTaskService.updateTodoTask(id, todoTaskRequest);
        return ResponseEntity.ok(todoTaskResponse);
    }

    /**
     * Deletes a task by its identifier.
     *
     * @param id the unique identifier of the task to delete
     * @return empty response indicating successful deletion
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete todo task by id", description = "Deletes a todo task by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Todo task deleted successfully")
    public ResponseEntity<Void> deleteTodoTask(@PathVariable Long id) {
        this.todoTaskService.deleteTodoTask(id);
        return ResponseEntity.ok().build();
    }
}
