package io.codepred.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a task is not found.
 * <p>
 * This exception is raised when attempting to retrieve, update,
 * or delete a task that does not exist in the database.
 * Automatically mapped to HTTP 404 Not Found response.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Todo task not found")
public class TodoTaskNotFoundException extends RuntimeException {
}
