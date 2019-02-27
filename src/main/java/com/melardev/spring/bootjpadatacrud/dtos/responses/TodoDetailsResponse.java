package com.melardev.spring.bootjpadatacrud.dtos.responses;

import com.melardev.spring.bootjpadatacrud.entities.Todo;

import java.time.LocalDateTime;
import java.util.Collection;

public class TodoDetailsResponse extends SuccessResponse {
    private final Long id;
    private String title, description;
    private boolean completed;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public TodoDetailsResponse(Long id, String title, String description, boolean completed, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public TodoDetailsResponse(Todo todo, String message) {
        this(todo.getId(), todo.getTitle(), todo.getDescription(), todo.isCompleted(), todo.getCreatedAt(), todo.getUpdatedAt());
        addFullMessage(message);
    }

    public TodoDetailsResponse(Todo todo) {
        this(todo, null);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getId() {
        return id;
    }
}
