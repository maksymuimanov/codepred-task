package io.codepred.task.dto;

import io.codepred.task.entity.TodoStatus;

import java.time.LocalDateTime;

public record TodoTaskResponse(
        Long id,
        String title,
        String description,
        TodoStatus status,
        LocalDateTime createdAt
) {
}
