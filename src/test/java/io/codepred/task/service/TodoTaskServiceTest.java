package io.codepred.task.service;

import io.codepred.task.dto.TodoTaskRequest;
import io.codepred.task.dto.TodoTaskResponse;
import io.codepred.task.entity.TodoStatus;
import io.codepred.task.entity.TodoTask;
import io.codepred.task.exception.TodoTaskNotFoundException;
import io.codepred.task.mapper.TodoTaskMapper;
import io.codepred.task.repository.TodoTaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

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

    @Test
    void getTodoTasks_shouldReturnTodoTaskPage() {
        Long id = 1L;
        TodoTask todoTask = new TodoTask(id, "Title", null, TodoStatus.NOT_STARTED, LocalDateTime.now());
        TodoTaskResponse todoTaskResponse = new TodoTaskResponse(todoTask.getId(), todoTask.getTitle(), todoTask.getDescription(), todoTask.getStatus(), todoTask.getCreatedAt());
        Page<TodoTask> todoTaskPage = new PageImpl<>(List.of(todoTask));
        Pageable pageable = mock(Pageable.class);

        when(todoTaskRepository.findAll(pageable))
                .thenReturn(todoTaskPage);
        when(todoTaskMapper.toTodoTaskResponse(todoTask))
                .thenReturn(todoTaskResponse);

        Page<TodoTaskResponse> todoTasks = todoTaskService.getTodoTasks(pageable);

        assertThat(todoTasks)
                .isNotNull()
                .hasSize(1)
                .contains(todoTaskResponse);
        verify(todoTaskRepository)
                .findAll(pageable);
        verify(todoTaskMapper)
                .toTodoTaskResponse(todoTask);
    }

    @Test
    void getTodoTask_shouldReturnTodoTask_whenTodoTaskExists() {
        Long id = 1L;
        TodoTask todoTask = new TodoTask(id, "Title", null, TodoStatus.NOT_STARTED, LocalDateTime.now());
        TodoTaskResponse todoTaskResponse = new TodoTaskResponse(todoTask.getId(), todoTask.getTitle(), todoTask.getDescription(), todoTask.getStatus(), todoTask.getCreatedAt());

        when(todoTaskRepository.findById(id))
                .thenReturn(Optional.of(todoTask));
        when(todoTaskMapper.toTodoTaskResponse(todoTask))
                .thenReturn(todoTaskResponse);

        TodoTaskResponse retrievedTodoTask = todoTaskService.getTodoTask(id);

        assertThat(retrievedTodoTask)
                .isNotNull()
                .isEqualTo(todoTaskResponse);
        verify(todoTaskRepository)
                .findById(id);
        verify(todoTaskMapper)
                .toTodoTaskResponse(todoTask);
    }

    @Test
    void getTodoTask_shouldThrowException_whenTodoTaskNotExists() {
        Long id = 1L;

        when(todoTaskRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> todoTaskService.getTodoTask(id))
                .isInstanceOf(TodoTaskNotFoundException.class);

        verify(todoTaskRepository)
                .findById(id);
        verify(todoTaskMapper, never())
                .toTodoTaskResponse(any());
    }

    @Test
    void updateTodoTask_shouldConvertRequestAndUpdateTodoTask() {
        Long id = 1L;
        TodoTaskRequest todoTaskRequest = new TodoTaskRequest("Title", null, TodoStatus.NOT_STARTED);
        TodoTask todoTask = spy(new TodoTask(id, todoTaskRequest.title(), todoTaskRequest.description(), todoTaskRequest.status(), LocalDateTime.now()));
        TodoTaskResponse todoTaskResponse = new TodoTaskResponse(todoTask.getId(), todoTask.getTitle(), todoTask.getDescription(), todoTask.getStatus(), todoTask.getCreatedAt());

        when(todoTaskRepository.findById(id))
                .thenReturn(Optional.of(todoTask));
        doNothing()
                .when(todoTaskMapper)
                .updateTodoTask(todoTaskRequest, todoTask);
        when(todoTaskRepository.save(todoTask))
                .thenReturn(todoTask);
        when(todoTaskMapper.toTodoTaskResponse(todoTask))
                .thenReturn(todoTaskResponse);

        TodoTaskResponse result = todoTaskService.updateTodoTask(id, todoTaskRequest);

        assertThat(result)
                .isNotNull()
                .isEqualTo(todoTaskResponse);
        verify(todoTaskRepository)
                .findById(id);
        verify(todoTaskMapper)
                .updateTodoTask(todoTaskRequest, todoTask);
        verify(todoTaskRepository)
                .save(todoTask);
        verify(todoTaskMapper)
                .toTodoTaskResponse(todoTask);
    }

    @Test
    void updateTodoTask_shouldThrowException_whenTodoTaskNotExists() {
        Long id = 1L;
        TodoTaskRequest todoTaskRequest = new TodoTaskRequest("Title", null, TodoStatus.NOT_STARTED);

        when(todoTaskRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> todoTaskService.updateTodoTask(id, todoTaskRequest))
                .isInstanceOf(TodoTaskNotFoundException.class);
    }

    @Test
    void deleteTodoTask_shouldDeleteTodoTask() {
        Long id = 1L;

        doNothing()
                .when(todoTaskRepository)
                .deleteById(id);

        todoTaskService.deleteTodoTask(id);

        verify(todoTaskRepository)
                .deleteById(id);
    }
}
