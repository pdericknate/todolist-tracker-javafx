package com.todolist.config;

import java.nio.file.Paths;

public class AppConfig {

    // Application Info
    public static final String APP_NAME = "To-Do List";
    public static final String APP_VERSION = "1.0.0";
    public static final String APP_TITLE = APP_NAME + " - v" + APP_VERSION;

    // Storage Configuration
    public static final String DATA_DIR = Paths.get(System.getProperty("user.home"), ".todolist", "data").toString();
    public static final String TASKS_FILE = Paths.get(DATA_DIR, "tasks.json").toString();
    public static final String BACKUP_DIR = Paths.get(System.getProperty("user.home"), ".todolist", "backup").toString();

    // UI Configuration
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 700;

    // Colors
    public static final String COLOR_HIGH_PRIORITY = "#e74c3c";
    public static final String COLOR_MEDIUM_PRIORITY = "#f39c12";
    public static final String COLOR_LOW_PRIORITY = "#27ae60";
    public static final String COLOR_COMPLETED = "#95a5a6";
    public static final String COLOR_OVERDUE = "#c0392b";
    public static final String COLOR_TODAY = "#3498db";

    // Style Constants
    public static final String PRIMARY_COLOR = "#2c3e50";
    public static final String SECONDARY_COLOR = "#34495e";
    public static final String ACCENT_COLOR = "#1abc9c";
    public static final String BACKGROUND_COLOR = "#ecf0f1";
    public static final String TEXT_COLOR = "#2c3e50";
    public static final String LIGHT_TEXT = "#ffffff";
    public static final String BORDER_COLOR = "#bdc3c7";

    // Priority Levels
    public enum Priority {
        HIGH("High", COLOR_HIGH_PRIORITY),
        MEDIUM("Medium", COLOR_MEDIUM_PRIORITY),
        LOW("Low", COLOR_LOW_PRIORITY);

        private final String displayName;
        private final String color;

        Priority(String displayName, String color) {
            this.displayName = displayName;
            this.color = color;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getColor() {
            return color;
        }
    }

    // Task Status
    public enum TaskStatus {
        ALL("All"),
        ACTIVE("Active"),
        COMPLETED("Completed");

        private final String displayName;

        TaskStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
