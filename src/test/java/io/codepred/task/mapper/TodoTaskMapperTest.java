package io.codepred.task.mapper;

import io.codepred.task.dto.TodoTaskRequest;
import io.codepred.task.dto.TodoTaskResponse;
import io.codepred.task.entity.TodoStatus;
import io.codepred.task.entity.TodoTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TodoTaskMapperTest {
    private TodoTaskMapper todoTaskMapper;

    @BeforeEach
    void setUp() {
        todoTaskMapper = Mappers.getMapper(TodoTaskMapper.class);
    }

    @Test
    void toTodoTask_shouldConvertTodoTaskRequestToTodoTask() {
        TodoTaskRequest todoTaskRequest = new TodoTaskRequest("Title", null, TodoStatus.NOT_STARTED);

        TodoTask todoTask = todoTaskMapper.toTodoTask(todoTaskRequest);

        assertThat(todoTask.getId())
                .isNull();
        assertThat(todoTask)
                .hasFieldOrPropertyWithValue("title", todoTaskRequest.title())
                .hasFieldOrPropertyWithValue("description", todoTaskRequest.description())
                .hasFieldOrPropertyWithValue("status", todoTaskRequest.status());
        assertThat(todoTask.getCreatedAt())
                .isNotNull();
    }

    @Test
    void toTodoTaskResponse_shouldConvertTodoTaskToTodoTaskResponse() {
        TodoTask todoTask = new TodoTask(1L, "Title", "Description", TodoStatus.NOT_STARTED, LocalDateTime.now());

        TodoTaskResponse todoTaskResponse = todoTaskMapper.toTodoTaskResponse(todoTask);

        assertThat(todoTaskResponse)
                .hasFieldOrPropertyWithValue("id", todoTask.getId())
                .hasFieldOrPropertyWithValue("title", todoTask.getTitle())
                .hasFieldOrPropertyWithValue("description", todoTask.getDescription())
                .hasFieldOrPropertyWithValue("status", todoTask.getStatus())
                .hasFieldOrPropertyWithValue("createdAt", todoTask.getCreatedAt());
    }
}
