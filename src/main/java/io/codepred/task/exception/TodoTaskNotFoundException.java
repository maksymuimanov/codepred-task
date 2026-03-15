package io.codepred.task.exception;

public class TodoTaskNotFoundException extends RuntimeException {
  public TodoTaskNotFoundException(String message) {
    super(message);
  }
}
