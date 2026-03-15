package io.codepred.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Todo task not found")
public class TodoTaskNotFoundException extends RuntimeException {
}
