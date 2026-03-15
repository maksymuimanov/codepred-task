package io.codepred.task.dto;

import io.codepred.task.entity.TodoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.Nullable;

public record TodoTaskRequest(
        @NotBlank
        String title,
        @Nullable
        String description,
        @NotNull
        TodoStatus status
) {
}
