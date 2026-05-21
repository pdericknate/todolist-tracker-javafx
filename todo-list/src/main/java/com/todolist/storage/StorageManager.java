package com.todolist.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.todolist.config.AppConfig;
import com.todolist.models.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class StorageManager {

    private static final Logger logger = LoggerFactory.getLogger(StorageManager.class);
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();
    private static List<Task> tasks = new ArrayList<>();

    public static void initialize() {
        try {
            // Create directories if they don't exist
            Files.createDirectories(Paths.get(AppConfig.DATA_DIR));
            Files.createDirectories(Paths.get(AppConfig.BACKUP_DIR));
            logger.info("Storage directories created");

            // Load tasks from file
            loadTasks();
        } catch (IOException e) {
            logger.error("Failed to initialize storage", e);
        }
    }

    public static void loadTasks() {
        try {
            File file = new File(AppConfig.TASKS_FILE);
            if (file.exists()) {
                String json = new String(Files.readAllBytes(Paths.get(AppConfig.TASKS_FILE)));
                tasks = gson.fromJson(json, new TypeToken<List<Task>>() {}.getType());
                if (tasks == null) {
                    tasks = new ArrayList<>();
                }
                logger.info("Loaded {} tasks from storage", tasks.size());
            } else {
                tasks = new ArrayList<>();
                logger.info("No existing tasks file, creating new one");
            }
        } catch (IOException e) {
            logger.error("Failed to load tasks", e);
            tasks = new ArrayList<>();
        }
    }

    public static void saveTasks() {
        try {
            String json = gson.toJson(tasks);
            Files.write(Paths.get(AppConfig.TASKS_FILE), json.getBytes());
            logger.info("Saved {} tasks to storage", tasks.size());
        } catch (IOException e) {
            logger.error("Failed to save tasks", e);
        }
    }

    // CRUD Operations
    public static void addTask(Task task) {
        tasks.add(task);
        saveTasks();
        logger.info("Task added: {}", task.getTitle());
    }

    public static void updateTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                tasks.set(i, task);
                saveTasks();
                logger.info("Task updated: {}", task.getTitle());
                return;
            }
        }
    }

    public static void deleteTask(long id) {
        tasks.removeIf(task -> task.getId() == id);
        saveTasks();
        logger.info("Task deleted: {}", id);
    }

    public static Task getTask(long id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public static List<Task> getActiveTasks() {
        List<Task> active = new ArrayList<>();
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                active.add(task);
            }
        }
        return active;
    }

    public static List<Task> getCompletedTasks() {
        List<Task> completed = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isCompleted()) {
                completed.add(task);
            }
        }
        return completed;
    }

    public static List<Task> getOverdueTasks() {
        List<Task> overdue = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isOverdue()) {
                overdue.add(task);
            }
        }
        return overdue;
    }

    public static List<Task> getTasksByPriority(String priority) {
        List<Task> filtered = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getPriority().equals(priority)) {
                filtered.add(task);
            }
        }
        return filtered;
    }

    public static List<Task> searchTasks(String query) {
        List<Task> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for (Task task : tasks) {
            if (task.getTitle().toLowerCase().contains(lowerQuery) ||
                (task.getDescription() != null && task.getDescription().toLowerCase().contains(lowerQuery))) {
                results.add(task);
            }
        }
        return results;
    }

    public static int getTotalTaskCount() {
        return tasks.size();
    }

    public static int getActiveTaskCount() {
        return (int) tasks.stream().filter(t -> !t.isCompleted()).count();
    }

    public static int getCompletedTaskCount() {
        return (int) tasks.stream().filter(Task::isCompleted).count();
    }

    public static int getOverdueTaskCount() {
        return (int) tasks.stream().filter(Task::isOverdue).count();
    }

    public static double getCompletionPercentage() {
        if (tasks.isEmpty()) {
            return 0;
        }
        return (getCompletedTaskCount() / (double) tasks.size()) * 100;
    }
}
