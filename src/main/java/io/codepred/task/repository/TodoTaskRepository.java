package io.codepred.task.repository;

import io.codepred.task.entity.TodoTask;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for task entities.
 * <p>
 * Provides database access operations for task persistence.
 * Extends JpaRepository to inherit standard CRUD operations
 * and adds task-specific query methods when needed.
 */
public interface TodoTaskRepository extends JpaRepository<TodoTask, Long> {
}
