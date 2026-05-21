package com.todolist.ui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import com.todolist.config.AppConfig;
import com.todolist.models.Task;
import com.todolist.storage.StorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TaskListView {

    private static final Logger logger = LoggerFactory.getLogger(TaskListView.class);
    private VBox mainBox;
    private VBox taskContainer;
    private ComboBox<String> statusFilter;
    private ComboBox<String> priorityFilter;
    private TextField searchField;

    public VBox getView() {
        mainBox = new VBox();
        mainBox.setStyle("-fx-padding: 10; -fx-spacing: 10; -fx-background-color: " + AppConfig.BACKGROUND_COLOR + ";");

        // Filters
        mainBox.getChildren().add(createFilterBar());

        // Task Container
        ScrollPane scrollPane = new ScrollPane();
        taskContainer = new VBox();
        taskContainer.setSpacing(5);
        taskContainer.setStyle("-fx-padding: 5;");
        scrollPane.setContent(taskContainer);
        scrollPane.setFitToWidth(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        mainBox.getChildren().add(scrollPane);

        // Load tasks
        refreshTaskList();

        return mainBox;
    }

    private HBox createFilterBar() {
        HBox filterBar = new HBox();
        filterBar.setSpacing(10);
        filterBar.setAlignment(Pos.CENTER_LEFT);
        filterBar.setStyle("-fx-padding: 10; -fx-background-color: white; -fx-border-color: " + AppConfig.BORDER_COLOR + "; -fx-border-radius: 5;");

        // Status Filter
        Label statusLabel = new Label("Status:");
        statusLabel.setStyle("-fx-font-size: 11; -fx-text-fill: " + AppConfig.TEXT_COLOR + ";");
        statusFilter = new ComboBox<>();
        statusFilter.getItems().addAll("All", "Active", "Completed");
        statusFilter.setValue("All");
        statusFilter.setPrefWidth(100);
        statusFilter.setOnAction(e -> refreshTaskList());

        // Priority Filter
        Label priorityLabel = new Label("Priority:");
        priorityLabel.setStyle("-fx-font-size: 11; -fx-text-fill: " + AppConfig.TEXT_COLOR + ";");
        priorityFilter = new ComboBox<>();
        priorityFilter.getItems().addAll("All", "High", "Medium", "Low");
        priorityFilter.setValue("All");
        priorityFilter.setPrefWidth(100);
        priorityFilter.setOnAction(e -> refreshTaskList());

        // Search
        Label searchLabel = new Label("Search:");
        searchLabel.setStyle("-fx-font-size: 11; -fx-text-fill: " + AppConfig.TEXT_COLOR + ";");
        searchField = new TextField();
        searchField.setPromptText("Search tasks...");
        searchField.setPrefWidth(200);
        searchField.setOnKeyReleased(e -> refreshTaskList());

        filterBar.getChildren().addAll(
                statusLabel, statusFilter,
                priorityLabel, priorityFilter,
                searchLabel, searchField
        );

        return filterBar;
    }

    public void refreshTaskList() {
        taskContainer.getChildren().clear();
        List<Task> tasks = StorageManager.getAllTasks();

        // Apply filters
        String status = statusFilter.getValue();
        String priority = priorityFilter.getValue();
        String search = searchField.getText();

        tasks.removeIf(task -> {
            // Status filter
            if ("Active".equals(status) && task.isCompleted()) return true;
            if ("Completed".equals(status) && !task.isCompleted()) return true;

            // Priority filter
            if (!"All".equals(priority) && !task.getPriority().equals(priority.toUpperCase())) return true;

            // Search filter
            if (!search.isEmpty() && !task.getTitle().toLowerCase().contains(search.toLowerCase()) &&
                (task.getDescription() == null || !task.getDescription().toLowerCase().contains(search.toLowerCase()))) {
                return true;
            }

            return false;
        });

        // Sort tasks (overdue first, then by priority)
        tasks.sort((t1, t2) -> {
            if (t1.isCompleted() != t2.isCompleted()) {
                return t1.isCompleted() ? 1 : -1;
            }
            if (t1.isOverdue() != t2.isOverdue()) {
                return t1.isOverdue() ? -1 : 1;
            }
            return 0;
        });

        if (tasks.isEmpty()) {
            Label emptyLabel = new Label("No tasks found");
            emptyLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #999; -fx-padding: 50;");
            taskContainer.getChildren().add(emptyLabel);
        } else {
            for (Task task : tasks) {
                taskContainer.getChildren().add(createTaskItem(task));
            }
        }
    }

    private HBox createTaskItem(Task task) {
        HBox taskBox = new HBox();
        taskBox.setSpacing(10);
        taskBox.setAlignment(Pos.CENTER_LEFT);
        taskBox.setPrefHeight(60);
        taskBox.setStyle("-fx-padding: 10; -fx-border-color: " + AppConfig.BORDER_COLOR + "; -fx-border-radius: 5; -fx-background-color: white;");

        // Checkbox
        CheckBox checkbox = new CheckBox();
        checkbox.setSelected(task.isCompleted());
        checkbox.setOnAction(e -> {
            task.setCompleted(checkbox.isSelected());
            StorageManager.updateTask(task);
            refreshTaskList();
        });

        // Priority Indicator
        Label priorityLabel = new Label("●");
        priorityLabel.setStyle("-fx-font-size: 20; -fx-text-fill: " + getPriorityColor(task) + ";");

        // Task Info
        VBox infoBox = new VBox();
        infoBox.setSpacing(3);

        Label titleLabel = new Label(task.getTitle());
        String titleStyle = "-fx-font-size: 13; -fx-text-fill: " + AppConfig.TEXT_COLOR + ";";
        if (task.isCompleted()) {
            titleStyle += " -fx-text-fill: #999; -fx-strikethrough: true;";
        }
        if (task.isOverdue()) {
            titleStyle += " -fx-font-weight: bold;";
        }
        titleLabel.setStyle(titleStyle);

        Label dueDateLabel = new Label(task.getDueDateFormatted());
        String dateStyle = "-fx-font-size: 10; -fx-text-fill: #999;";
        if (task.isOverdue()) {
            dateStyle = "-fx-font-size: 10; -fx-text-fill: " + AppConfig.COLOR_OVERDUE + "; -fx-font-weight: bold;";
        } else if (task.isDueToday()) {
            dateStyle = "-fx-font-size: 10; -fx-text-fill: " + AppConfig.COLOR_TODAY + "; -fx-font-weight: bold;";
        }
        dueDateLabel.setStyle(dateStyle);

        infoBox.getChildren().addAll(titleLabel, dueDateLabel);

        // Edit Button
        Button editBtn = new Button("Edit");
        editBtn.setStyle("-fx-padding: 5 15; -fx-font-size: 10; -fx-background-color: #3498db; -fx-text-fill: white;");
        editBtn.setOnAction(e -> new TaskFormDialog(task, this).show());

        // Delete Button
        Button deleteBtn = new Button("Delete");
        deleteBtn.setStyle("-fx-padding: 5 15; -fx-font-size: 10; -fx-background-color: #e74c3c; -fx-text-fill: white;");
        deleteBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Task");
            alert.setContentText("Are you sure you want to delete this task?");
            if (alert.showAndWait().isPresent()) {
                StorageManager.deleteTask(task.getId());
                refreshTaskList();
                logger.info("Task deleted: {}", task.getTitle());
            }
        });

        taskBox.getChildren().addAll(checkbox, priorityLabel, infoBox);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        taskBox.getChildren().add(spacer);
        taskBox.getChildren().addAll(editBtn, deleteBtn);

        return taskBox;
    }

    private String getPriorityColor(Task task) {
        return switch (task.getPriority()) {
            case "HIGH" -> AppConfig.COLOR_HIGH_PRIORITY;
            case "LOW" -> AppConfig.COLOR_LOW_PRIORITY;
            default -> AppConfig.COLOR_MEDIUM_PRIORITY;
        };
    }
}
