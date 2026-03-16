package io.codepred.task.e2e;

import io.codepred.task.Application;
import io.codepred.task.TestcontainersConfiguration;
import io.codepred.task.dto.TodoTaskRequest;
import io.codepred.task.entity.TodoStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = {
        Application.class,
        TestcontainersConfiguration.class
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TodoTaskApplicationE2ETest {
    public static final String TASKS_URL = "/api/v1.0/tasks";
    public static final String TASK_URL = "/api/v1.0/tasks/{id}";
    private RestTestClient testClient;

    @BeforeAll
    void beforeAll(WebApplicationContext webApplicationContext) {
        testClient = RestTestClient.bindToApplicationContext(webApplicationContext).build();
        TodoTaskRequest todoTaskRequest1 = new TodoTaskRequest("Title 1", "Description 1", TodoStatus.NOT_STARTED);
        TodoTaskRequest todoTaskRequest2 = new TodoTaskRequest("Title 2", "Description 2", TodoStatus.STARTED);
        TodoTaskRequest todoTaskRequest3 = new TodoTaskRequest("Title 3", "Description 3", TodoStatus.IN_PROGRESS);
        testClient.post()
                .uri(TASKS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoTaskRequest1)
                .exchange();
        testClient.post()
                .uri(TASKS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoTaskRequest2)
                .exchange();
        testClient.post()
                .uri(TASKS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoTaskRequest2)
                .exchange();
        testClient.post()
                .uri(TASKS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoTaskRequest3)
                .exchange();
    }

    @Test
    void postTodoTask_shouldReturnOkStatusAndCreateTodoTask() {
        TodoTaskRequest todoTaskRequest = new TodoTaskRequest("Title", null, TodoStatus.NOT_STARTED);

        testClient.post()
                .uri(TASKS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoTaskRequest)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id")
                .isNotEmpty()
                .jsonPath("$.title")
                .isEqualTo(todoTaskRequest.title())
                .jsonPath("$.description")
                .isEqualTo(todoTaskRequest.description())
                .jsonPath("$.status")
                .isEqualTo(todoTaskRequest.status().name())
                .jsonPath("$.createdAt")
                .isNotEmpty();
    }

    @Test
    void postTodoTask_shouldReturnBadRequestStatusAndNotCreateTodoTask_whenInvalidTodoTaskRequest() {
        TodoTaskRequest todoTaskRequest = new TodoTaskRequest("", null, null);

        testClient.post()
                .uri(TASKS_URL)
                .accept(MediaType.APPLICATION_JSON)
                .body(todoTaskRequest)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void getTodoTasks_shouldReturnOkStatusAndTodoTaskPage() {
        testClient.get()
                .uri(TASKS_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.content")
                .hasJsonPath();
    }

    @Test
    void getTodoTask_shouldReturnOkStatusAndGetTodoTask() {
        testClient.get()
                .uri(TASK_URL, 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id")
                .isEqualTo(1)
                .jsonPath("$.title")
                .isEqualTo("Title 1")
                .jsonPath("$.description")
                .isEqualTo("Description 1")
                .jsonPath("$.status")
                .isEqualTo(TodoStatus.NOT_STARTED.name())
                .jsonPath("$.createdAt")
                .isNotEmpty();
    }

    @Test
    void getTodoTask_shouldReturnNotFoundStatusAndNotGetTodoTask_whenTodoTaskNotExists() {
        testClient.get()
                .uri(TASK_URL, -1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void putTodoTask_shouldReturnOkStatusAndUpdateTodoTask() {
        TodoTaskRequest todoTaskRequest = new TodoTaskRequest("New Title", "New Description", TodoStatus.FINISHED);

        testClient.put()
                .uri(TASK_URL, 2)
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoTaskRequest)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id")
                .isEqualTo(2)
                .jsonPath("$.title")
                .isEqualTo(todoTaskRequest.title())
                .jsonPath("$.description")
                .isEqualTo(todoTaskRequest.description())
                .jsonPath("$.status")
                .isEqualTo(todoTaskRequest.status().name())
                .jsonPath("$.createdAt")
                .isNotEmpty();
    }

    @Test
    void putTodoTask_shouldReturnNotFoundAndNotUpdateTodoTask_whenInvalidTodoTaskRequest() {
        TodoTaskRequest todoTaskRequest = new TodoTaskRequest("New Title", "New Description", TodoStatus.FINISHED);

        testClient.put()
                .uri(TASK_URL, -1)
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoTaskRequest)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void putTodoTask_shouldReturnBadRequestAndNotUpdateTodoTask_whenInvalidTodoTaskRequest() {
        TodoTaskRequest todoTaskRequest = new TodoTaskRequest("", null, null);

        testClient.put()
                .uri(TASK_URL, 2)
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoTaskRequest)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void deleteTodoTask_shouldReturnOkStatusAndDeleteTodoTask() {
        testClient.delete()
                .uri(TASK_URL, 3)
                .exchange()
                .expectStatus()
                .isOk();
    }
}