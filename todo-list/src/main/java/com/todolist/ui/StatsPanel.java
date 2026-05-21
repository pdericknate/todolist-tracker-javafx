package com.todolist.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import com.todolist.config.AppConfig;
import com.todolist.storage.StorageManager;

public class StatsPanel {

    public VBox getView() {
        VBox panel = new VBox();
        panel.setPrefWidth(250);
        panel.setStyle("-fx-padding: 15; -fx-spacing: 15; -fx-background-color: white; -fx-border-color: " + AppConfig.BORDER_COLOR + "; -fx-border-radius: 5;");
        panel.setAlignment(Pos.TOP_CENTER);

        // Title
        Label titleLabel = new Label("Statistics");
        titleLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: " + AppConfig.TEXT_COLOR + ";");
        panel.getChildren().add(titleLabel);

        // Total Tasks
        panel.getChildren().add(createStatLine(
                "Total Tasks",
                String.valueOf(StorageManager.getTotalTaskCount()),
                "#2c3e50"
        ));

        // Active Tasks
        panel.getChildren().add(createStatLine(
                "Active Tasks",
                String.valueOf(StorageManager.getActiveTaskCount()),
                "#3498db"
        ));

        // Completed Tasks
        panel.getChildren().add(createStatLine(
                "Completed Tasks",
                String.valueOf(StorageManager.getCompletedTaskCount()),
                "#27ae60"
        ));

        // Overdue Tasks
        panel.getChildren().add(createStatLine(
                "Overdue Tasks",
                String.valueOf(StorageManager.getOverdueTaskCount()),
                "#e74c3c"
        ));

        // Completion Percentage
        panel.getChildren().add(new Label(""));
        Label completionLabel = new Label("Completion");
        completionLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: " + AppConfig.TEXT_COLOR + ";");
        panel.getChildren().add(completionLabel);

        double percentage = StorageManager.getCompletionPercentage();
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(percentage / 100.0);
        progressBar.setPrefWidth(200);
        progressBar.setStyle("-fx-accent: " + AppConfig.ACCENT_COLOR + ";");
        panel.getChildren().add(progressBar);

        Label percentLabel = new Label(String.format("%.1f%%", percentage));
        percentLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #666;");
        panel.getChildren().add(percentLabel);

        return panel;
    }

    private VBox createStatLine(String label, String value, String color) {
        VBox line = new VBox();
        line.setSpacing(3);
        line.setStyle("-fx-border-color: #eee; -fx-border-width: 0 0 1 0; -fx-padding: 10 0;");

        Label labelText = new Label(label);
        labelText.setStyle("-fx-font-size: 11; -fx-text-fill: #999;");

        Label valueText = new Label(value);
        valueText.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        line.getChildren().addAll(labelText, valueText);
        return line;
    }
}
