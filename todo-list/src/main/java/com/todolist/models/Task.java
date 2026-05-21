package com.todolist.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private long id;
    private String title;
    private String description;
    private String priority; // HIGH, MEDIUM, LOW
    private LocalDate dueDate;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task() {
        this.id = System.currentTimeMillis();
        this.completed = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.priority = "MEDIUM";
    }

    public Task(String title, String description, String priority, LocalDate dueDate) {
        this();
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isOverdue() {
        if (dueDate == null || completed) {
            return false;
        }
        return dueDate.isBefore(LocalDate.now());
    }

    public boolean isDueToday() {
        if (dueDate == null) {
            return false;
        }
        return dueDate.isEqual(LocalDate.now());
    }

    public String getDueDateFormatted() {
        if (dueDate == null) {
            return "No due date";
        }
        if (isDueToday()) {
            return "Today";
        }
        if (isOverdue()) {
            return "Overdue: " + dueDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
        }
        return dueDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", priority='" + priority + '\'' +
                ", completed=" + completed +
                ", dueDate=" + dueDate +
                '}';
    }
}
