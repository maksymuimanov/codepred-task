package io.codepred.task.dto;

import io.codepred.task.entity.TodoStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.Nullable;

/**
 * Data transfer object for creating and updating tasks.
 * <p>
 * Represents the request payload for task creation and update operations.
 * Contains the task title, optional description, and current status.
 */
public record TodoTaskRequest(
        @Schema(description = "Title of the task", example = "Task title")
        @NotBlank
        String title,
        @Schema(description = "Description of the task", example = "Task description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Nullable
        String description,
        @Schema(description = "Status of the task", example = "NOT_STARTED")
        @NotNull
        TodoStatus status
) {
}
