package io.codepred.task.service;

import io.codepred.task.dto.TodoTaskRequest;
import io.codepred.task.dto.TodoTaskResponse;
import io.codepred.task.entity.TodoStatus;
import io.codepred.task.entity.TodoTask;
import io.codepred.task.mapper.TodoTaskMapper;
import io.codepred.task.repository.TodoTaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoTaskServiceTest {
    @InjectMocks
    private TodoTaskServiceImpl todoTaskService;
    @Mock
    private TodoTaskMapper todoTaskMapper;
    @Mock
    private TodoTaskRepository todoTaskRepository;

    @Test
    void createTodoTask_shouldConvertRequestAndSaveToDatabase() {
        TodoTaskRequest todoTaskRequest = new TodoTaskRequest("Title", null, TodoStatus.NOT_STARTED);
        TodoTask todoTask = new TodoTask(null, todoTaskRequest.title(), todoTaskRequest.description(), todoTaskRequest.status(), LocalDateTime.now());
        TodoTaskResponse todoTaskResponse = new TodoTaskResponse(todoTask.getId(), todoTask.getTitle(), todoTask.getDescription(), todoTask.getStatus(), todoTask.getCreatedAt());

        when(todoTaskMapper.toTodoTask(todoTaskRequest))
                .thenReturn(todoTask);
        when(todoTaskRepository.save(todoTask))
                .then(invocationOnMock -> {
                    todoTask.setId(1L);
                    return todoTask;
                });
        when(todoTaskMapper.toTodoTaskResponse(todoTask))
                .thenReturn(todoTaskResponse);

        TodoTaskResponse result = todoTaskService.createTodoTask(todoTaskRequest);

        assertThat(result)
                .isNotNull()
                .isEqualTo(todoTaskResponse);
        verify(todoTaskMapper)
                .toTodoTask(todoTaskRequest);
        verify(todoTaskRepository)
                .save(todoTask);
        verify(todoTaskMapper)
                .toTodoTaskResponse(todoTask);
    }
}
