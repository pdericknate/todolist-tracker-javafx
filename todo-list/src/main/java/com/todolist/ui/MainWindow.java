package com.todolist.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.todolist.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainWindow {

    private static final Logger logger = LoggerFactory.getLogger(MainWindow.class);

    public void show(Stage primaryStage) {
        logger.info("Building main window UI");

        // Create BorderPane layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-font-family: 'Segoe UI'; -fx-background-color: " + AppConfig.BACKGROUND_COLOR + ";");

        // Top Navigation Bar
        root.setTop(createTopBar());

        // Main content area with TaskListView and StatsPanel
        HBox centerBox = new HBox();
        centerBox.setSpacing(10);
        centerBox.setStyle("-fx-padding: 10;");

        // Task List (left side)
        TaskListView taskListView = new TaskListView();
        HBox.setHgrow(taskListView.getView(), javafx.scene.layout.Priority.ALWAYS);
        centerBox.getChildren().add(taskListView.getView());

        // Statistics Panel (right side)
        StatsPanel statsPanel = new StatsPanel();
        centerBox.getChildren().add(statsPanel.getView());

        root.setCenter(centerBox);

        // Create Scene
        Scene scene = new Scene(root, AppConfig.WINDOW_WIDTH, AppConfig.WINDOW_HEIGHT);
        primaryStage.setTitle(AppConfig.APP_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();

        logger.info("Main window displayed");
    }

    private VBox createTopBar() {
        VBox topBar = new VBox();
        topBar.setStyle("-fx-background-color: " + AppConfig.PRIMARY_COLOR + "; -fx-padding: 10; -fx-spacing: 10;");
        topBar.setPrefHeight(80);

        // Title
        Label titleLabel = new Label("✓ " + AppConfig.APP_TITLE);
        titleLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: " + AppConfig.LIGHT_TEXT + ";");

        // Button Bar
        HBox buttonBar = new HBox();
        buttonBar.setSpacing(10);
        buttonBar.setAlignment(Pos.CENTER_LEFT);

        Button newTaskBtn = new Button("+ New Task");
        newTaskBtn.setStyle(
                "-fx-padding: 8 20; " +
                        "-fx-font-size: 12; " +
                        "-fx-background-color: " + AppConfig.ACCENT_COLOR + "; " +
                        "-fx-text-fill: " + AppConfig.LIGHT_TEXT + "; " +
                        "-fx-cursor: hand;"
        );
        newTaskBtn.setOnAction(e -> new TaskFormDialog().show());

        buttonBar.getChildren().add(newTaskBtn);
        topBar.getChildren().addAll(titleLabel, buttonBar);

        return topBar;
    }
}
