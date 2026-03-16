package io.codepred.task.repository;

import io.codepred.task.TestcontainersConfiguration;
import io.codepred.task.entity.TodoStatus;
import io.codepred.task.entity.TodoTask;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TodoTaskRepositoryTest {
    @Autowired
    private TodoTaskRepository todoTaskRepository;
    @Autowired
    private EntityManager entityManager;
    private TodoTask todoTask;

    @BeforeEach
    void setUp() {
        todoTask = new TodoTask("Title", null, TodoStatus.NOT_STARTED, LocalDateTime.now());
    }

    @Test
    void save_shouldInsertTodoTask_whenTodoTaskNotExists() {
        todoTaskRepository.save(todoTask);

        Long id = todoTask.getId();
        assertThat(id)
                .isNotNull();
        assertThat(todoTask)
                .isEqualTo(entityManager.find(TodoTask.class, id));
    }

    @Test
    void save_shouldUpdateTodoTask_whenTodoTaskExists() {
        todoTaskRepository.save(todoTask);
        TodoTask updatedTodoTask = new TodoTask(todoTask.getId(), "New Title", "Description", TodoStatus.STARTED, LocalDateTime.now());
        todoTaskRepository.save(updatedTodoTask);

        Long id = updatedTodoTask.getId();
        assertThat(id)
                .isNotNull()
                .isEqualTo(todoTask.getId());
        assertThat(updatedTodoTask)
                .isEqualTo(entityManager.find(TodoTask.class, id));
    }

    @Test
    void findAll_withPageable_shouldReturnTodoTaskPage() {
        todoTaskRepository.save(todoTask);

        Page<TodoTask> todoTaskPage = todoTaskRepository.findAll(PageRequest.of(0, 10));

        assertThat(todoTaskPage.getContent())
                .isNotEmpty()
                .contains(todoTask);
    }

    @Test
    void findById_shouldReturnTodoTask_whenTodoTaskExists() {
        todoTaskRepository.save(todoTask);

        TodoTask foundTodoTask = todoTaskRepository.findById(todoTask.getId())
                .orElse(null);

        assertThat(foundTodoTask)
                .isNotNull()
                .isEqualTo(todoTask);
    }

    @Test
    void findById_shouldReturnEmpty_whenTodoTaskNotExists() {
        TodoTask foundTodoTask = todoTaskRepository.findById(1000L)
                .orElse(null);

        assertThat(foundTodoTask)
                .isNull();
    }

    @Test
    void delete_shouldDeleteTodoTask() {
        todoTaskRepository.save(todoTask);
        todoTaskRepository.delete(todoTask);

        assertThat(entityManager.find(TodoTask.class, todoTask.getId()))
                .isNull();
    }
}
