package io.codepred.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.codepred.task.dto.TodoTaskRequest;
import io.codepred.task.dto.TodoTaskResponse;
import io.codepred.task.entity.TodoStatus;
import io.codepred.task.service.TodoTaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(TodoTaskController.class)
@Import(JacksonAutoConfiguration.class)
class TodoTaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private TodoTaskService todoTaskService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    @Test
    void postTodoTask_shouldReturnOkStatusAndCreateTodoTask() throws Exception {
        TodoTaskRequest todoTaskRequest = new TodoTaskRequest("Title", null, TodoStatus.NOT_STARTED);
        TodoTaskResponse todoTaskResponse = new TodoTaskResponse(1L, todoTaskRequest.title(), todoTaskRequest.description(), todoTaskRequest.status(), LocalDateTime.now());

        when(todoTaskService.createTodoTask(todoTaskRequest))
                .thenReturn(todoTaskResponse);
        mockMvc.perform(post("/api/v1.0/tasks")
                        .content(objectMapper.writeValueAsString(todoTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todoTaskResponse.id()))
                .andExpect(jsonPath("$.title").value(todoTaskResponse.title()))
                .andExpect(jsonPath("$.description").value(todoTaskResponse.description()))
                .andExpect(jsonPath("$.status").value(todoTaskResponse.status().name()))
                .andExpect(jsonPath("$.createdAt").exists());
        verify(todoTaskService)
                .createTodoTask(todoTaskRequest);
    }

    @Test
    void postTodoTask_shouldReturnBadRequestStatusAndNotCreateTodoTask_whenInvalidTodoTaskRequest() throws Exception {
        TodoTaskRequest todoTaskRequest = new TodoTaskRequest(null, null, null);
        TodoTaskResponse todoTaskResponse = mock(TodoTaskResponse.class);

        mockMvc.perform(post("/api/v1.0/tasks")
                        .content(objectMapper.writeValueAsString(todoTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(todoTaskService, never())
                .createTodoTask(todoTaskRequest);
    }

    @Test
    void getTodoTasks_shouldReturnOkStatusAndTodoTaskPage() throws Exception {
        TodoTaskResponse todoTaskResponse = new TodoTaskResponse(1L, "Title", null, TodoStatus.NOT_STARTED, LocalDateTime.now());
        PageImpl<TodoTaskResponse> todoTaskPage = new PageImpl<>(List.of(todoTaskResponse));

        when(todoTaskService.getTodoTasks(any()))
                .thenReturn(todoTaskPage);

        mockMvc.perform(get("/api/v1.0/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(todoTaskResponse.id()))
                .andExpect(jsonPath("$.content[0].title").value(todoTaskResponse.title()))
                .andExpect(jsonPath("$.content[0].description").value(todoTaskResponse.description()))
                .andExpect(jsonPath("$.content[0].status").value(todoTaskResponse.status().name()))
                .andExpect(jsonPath("$.content[0].createdAt").exists());
        verify(todoTaskService)
                .getTodoTasks(any());
    }

    @Test
    void getTodoTask_shouldReturnOkStatusAndTodoTask() throws Exception {
        Long id = 1L;
        TodoTaskResponse todoTaskResponse = new TodoTaskResponse(id, "Title", null, TodoStatus.NOT_STARTED, LocalDateTime.now());

        when(todoTaskService.getTodoTask(id))
                .thenReturn(todoTaskResponse);

        mockMvc.perform(get("/api/v1.0/tasks/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todoTaskResponse.id()))
                .andExpect(jsonPath("$.title").value(todoTaskResponse.title()))
                .andExpect(jsonPath("$.description").value(todoTaskResponse.description()))
                .andExpect(jsonPath("$.status").value(todoTaskResponse.status().name()))
                .andExpect(jsonPath("$.createdAt").exists());
        verify(todoTaskService)
                .getTodoTask(id);
    }

    @Test
    void putTodoTask_shouldReturnOkStatusAndUpdateTodoTask() throws Exception {
        Long id = 1L;
        TodoTaskRequest todoTaskRequest = new TodoTaskRequest("Title", null, TodoStatus.NOT_STARTED);
        TodoTaskResponse todoTaskResponse = new TodoTaskResponse(id, todoTaskRequest.title(), todoTaskRequest.description(), todoTaskRequest.status(), LocalDateTime.now());

        when(todoTaskService.updateTodoTask(id, todoTaskRequest))
                .thenReturn(todoTaskResponse);

        mockMvc.perform(put("/api/v1.0/tasks/{id}", id)
                .content(objectMapper.writeValueAsString(todoTaskRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todoTaskResponse.id()))
                .andExpect(jsonPath("$.title").value(todoTaskResponse.title()))
                .andExpect(jsonPath("$.description").value(todoTaskResponse.description()))
                .andExpect(jsonPath("$.status").value(todoTaskResponse.status().name()))
                .andExpect(jsonPath("$.createdAt").exists());
        verify(todoTaskService)
                .updateTodoTask(id, todoTaskRequest);
    }

    @Test
    void deleteTodoTask_shouldReturnOkStatusAndDeleteTodoTask() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/v1.0/tasks/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(todoTaskService)
                .deleteTodoTask(id);
    }
}
