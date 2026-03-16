package io.codepred.task.e2e;

import io.codepred.task.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.utility.TestcontainersConfiguration;

@SpringBootTest(classes = {
        Application.class,
        TestcontainersConfiguration.class
})
class TodoTaskApplicationE2ETest {
    private RestTestClient testClient;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        testClient = RestTestClient.bindToApplicationContext(webApplicationContext).build();
    }


    @Test
    void postTodoTask_shouldCreateTodoTask() throws Exception {
    }

    @Test
    void getTodoTasks_shouldReturnTodoTaskPage() throws Exception {
    }

    @Test
    void getTodoTask_shouldReturnTodoTask() throws Exception {
    }

    @Test
    void putTodoTask_shouldUpdateTodoTask() throws Exception {
    }

    @Test
    void deleteTodoTask_shouldDeleteTodoTask() throws Exception {
    }
}