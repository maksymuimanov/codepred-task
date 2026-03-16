package io.codepred.task.dto;

import io.codepred.task.entity.TodoStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;

/**
 * Data transfer object for task responses.
 * <p>
 * Represents the response payload for task retrieval operations.
 * Contains the complete task information, including the generated ID and creation timestamp.
 */
public record TodoTaskResponse(
        @Schema(description = "Unique identifier of the task", example = "1")
        Long id,
        @Schema(description = "Title of the task", example = "Task title")
        String title,
        @Schema(description = "Description of the task", example = "Task description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Nullable
        String description,
        @Schema(description = "Status of the task", example = "NOT_STARTED")
        TodoStatus status,
        @Schema(description = "Creation date and time of the task", example = "2026-03-15T00:00:00")
        LocalDateTime createdAt
) {
}
