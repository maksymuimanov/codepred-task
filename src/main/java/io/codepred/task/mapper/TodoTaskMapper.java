package io.codepred.task.mapper;

import io.codepred.task.dto.TodoTaskRequest;
import io.codepred.task.dto.TodoTaskResponse;
import io.codepred.task.entity.TodoTask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

/**
 * MapStruct mapper for converting between task DTOs and entities.
 * <p>
 * Provides bidirectional mapping between request/response DTOs
 * and JPA entities, handling field transformations and data conversions.
 */
@Mapper(componentModel = SPRING)
public interface TodoTaskMapper {
    /**
     * Converts a task request DTO to a new entity.
     * <p>
     * Maps request data to entity, ignoring ID and setting current timestamp.
     *
     * @param todoTaskRequest the request DTO to convert
     * @return new task entity with current creation time
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    TodoTask toTodoTask(TodoTaskRequest todoTaskRequest);

    /**
     * Converts a task entity to a response DTO.
     *
     * @param todoTask the entity to convert
     * @return response DTO containing all task data
     */
    TodoTaskResponse toTodoTaskResponse(TodoTask todoTask);

    /**
     * Updates an existing entity with data from a request DTO.
     * <p>
     * Copies non-null fields from the request to the existing entity.
     *
     * @param todoTaskRequest the request data to apply
     * @param todoTask the entity to update (target)
     */
    void updateTodoTask(TodoTaskRequest todoTaskRequest, @MappingTarget TodoTask todoTask);
}
