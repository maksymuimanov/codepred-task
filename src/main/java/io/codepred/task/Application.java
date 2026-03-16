package io.codepred.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Task Management Spring Boot application.
 * <p>
 * This class configures and launches the Spring Boot application
 * that provides REST API endpoints for managing tasks.
 */
@SpringBootApplication
public class Application {
    /**
     * Starts the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
