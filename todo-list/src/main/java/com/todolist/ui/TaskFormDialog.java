package com.todolist.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.todolist.config.AppConfig;
import com.todolist.models.Task;
import com.todolist.storage.StorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class TaskFormDialog {

    private static final Logger logger = LoggerFactory.getLogger(TaskFormDialog.class);
    private Task taskToEdit;
    private TaskListView taskListView;

    public TaskFormDialog() {
        this(null, null);
    }

    public TaskFormDialog(Task taskToEdit, TaskListView taskListView) {
        this.taskToEdit = taskToEdit;
        this.taskListView = taskListView;
    }

    public void show() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(taskToEdit == null ? "New Task" : "Edit Task");
        dialogStage.setWidth(500);
        dialogStage.setHeight(400);

        VBox root = new VBox();
        root.setStyle("-fx-padding: 20; -fx-spacing: 15; -fx-background-color: " + AppConfig.BACKGROUND_COLOR + ";");

        // Title
        VBox titleBox = createFormField("Title (Required)");
        TextField titleField = new TextField();
        titleField.setPrefHeight(35);
        if (taskToEdit != null) {
            titleField.setText(taskToEdit.getTitle());
        }
        titleBox.getChildren().add(titleField);
        root.getChildren().add(titleBox);

        // Description
        VBox descBox = createFormField("Description");
        TextArea descArea = new TextArea();
        descArea.setPrefHeight(80);
        descArea.setWrapText(true);
        if (taskToEdit != null && taskToEdit.getDescription() != null) {
            descArea.setText(taskToEdit.getDescription());
        }
        descBox.getChildren().add(descArea);
        root.getChildren().add(descBox);

        // Priority
        HBox priorityBox = new HBox();
        priorityBox.setSpacing(10);
        Label priorityLabel = new Label("Priority:");
        ComboBox<String> priorityCombo = new ComboBox<>();
        priorityCombo.getItems().addAll("High", "Medium", "Low");
        priorityCombo.setValue(taskToEdit != null ? taskToEdit.getPriority().substring(0, 1).toUpperCase() + taskToEdit.getPriority().substring(1).toLowerCase() : "Medium");
        priorityCombo.setPrefWidth(150);
        priorityBox.getChildren().addAll(priorityLabel, priorityCombo);
        root.getChildren().add(priorityBox);

        // Due Date
        HBox dateBox = new HBox();
        dateBox.setSpacing(10);
        Label dateLabel = new Label("Due Date:");
        DatePicker datePicker = new DatePicker();
        if (taskToEdit != null && taskToEdit.getDueDate() != null) {
            datePicker.setValue(taskToEdit.getDueDate());
        }
        datePicker.setPrefWidth(200);
        dateBox.getChildren().addAll(dateLabel, datePicker);
        root.getChildren().add(dateBox);

        // Buttons
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        Button saveBtn = new Button("Save");
        saveBtn.setStyle("-fx-padding: 10 30; -fx-background-color: " + AppConfig.ACCENT_COLOR + "; -fx-text-fill: white; -fx-cursor: hand;");
        saveBtn.setOnAction(e -> {
            String title = titleField.getText().trim();
            if (title.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Title cannot be empty!");
                return;
            }

            if (taskToEdit == null) {
                Task newTask = new Task(
                        title,
                        descArea.getText(),
                        priorityCombo.getValue().toUpperCase(),
                        datePicker.getValue()
                );
                StorageManager.addTask(newTask);
                logger.info("New task created: {}", title);
            } else {
                taskToEdit.setTitle(title);
                taskToEdit.setDescription(descArea.getText());
                taskToEdit.setPriority(priorityCombo.getValue().toUpperCase());
                taskToEdit.setDueDate(datePicker.getValue());
                StorageManager.updateTask(taskToEdit);
                logger.info("Task updated: {}", title);
            }

            if (taskListView != null) {
                taskListView.refreshTaskList();
            }

            dialogStage.close();
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-padding: 10 30; -fx-background-color: #95a5a6; -fx-text-fill: white; -fx-cursor: hand;");
        cancelBtn.setOnAction(e -> dialogStage.close());

        buttonBox.getChildren().addAll(saveBtn, cancelBtn);
        root.getChildren().add(buttonBox);

        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    private VBox createFormField(String label) {
        VBox box = new VBox();
        box.setSpacing(5);
        Label fieldLabel = new Label(label);
        fieldLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: " + AppConfig.TEXT_COLOR + ";");
        box.getChildren().add(fieldLabel);
        return box;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
