package com.todolist;

import javafx.application.Application;
import javafx.stage.Stage;
import com.todolist.config.AppConfig;
import com.todolist.storage.StorageManager;
import com.todolist.ui.MainWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TodoListApp extends Application {

    private static final Logger logger = LoggerFactory.getLogger(TodoListApp.class);

    public static void main(String[] args) {
        logger.info("Starting To-Do List Application...");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            logger.info("Initializing application");
            
            // Initialize storage
            StorageManager.initialize();
            logger.info("Storage initialized");

            // Create and show main window
            MainWindow mainWindow = new MainWindow();
            mainWindow.show(primaryStage);

            logger.info("Application started successfully");
        } catch (Exception e) {
            logger.error("Failed to start application", e);
            System.exit(1);
        }
    }

    @Override
    public void stop() {
        logger.info("Closing To-Do List Application");
        StorageManager.saveTasks();
    }
}
