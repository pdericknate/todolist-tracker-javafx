# To-Do List JavaFX Application

![Java](https://img.shields.io/badge/Java-17+-blue)
![JavaFX](https://img.shields.io/badge/JavaFX-21.0.2-green)
![License](https://img.shields.io/badge/license-MIT-brightgreen)

A modern, feature-rich To-Do List desktop application built with **JavaFX** and **Local JSON Storage**.

## ✨ Features

### 📝 Task Management
- ✅ Create new tasks with title, description, priority & due date
- ✅ Edit existing tasks
- ✅ Mark tasks as completed
- ✅ Delete tasks with confirmation
- ✅ Set priority levels (High, Medium, Low)
- ✅ Assign due dates to tasks

### 💾 Local Storage
- ✅ Auto-save to JSON file
- ✅ Load tasks on startup
- ✅ Persistent data storage in `~/.todolist/data/tasks.json`
- ✅ Automatic backup on every change
- ✅ No database required

### 🔍 Filtering & Search
- ✅ Filter by status (All, Active, Completed)
- ✅ Filter by priority (High, Medium, Low)
- ✅ Search functionality
- ✅ Real-time results

### 📊 Statistics Dashboard
- ✅ Total tasks count
- ✅ Active vs Completed tasks
- ✅ Overdue tasks tracking
- ✅ Priority breakdown
- ✅ Completion percentage

### 🎨 Modern UI
- ✅ Clean, professional interface
- ✅ Color-coded priorities
- ✅ Overdue task indicators
- ✅ Due date tracking (Today, Overdue, etc.)
- ✅ Dark/Light theme support
- ✅ Responsive design

## 🛠️ Tech Stack

- **UI Framework**: JavaFX 21.0.2
- **Data Storage**: JSON (Local File)
- **JSON Library**: Gson
- **Logging**: SLF4J + Logback
- **Build Tool**: Maven
- **Java Version**: 17+

## 📁 Project Structure

```
todo-list-javafx/
├── src/main/java/com/todolist/
│   ├── TodoListApp.java          # Main entry point
│   ├── config/
│   │   └── AppConfig.java        # Configuration & constants
│   ├── models/
│   │   └── Task.java             # Task data model
│   ├── storage/
│   │   └── StorageManager.java   # JSON storage (CRUD)
│   ├── ui/
│   │   ├── MainWindow.java       # Main UI layout
│   │   ├── TaskListView.java     # Task list component
│   │   ├── TaskFormDialog.java   # Add/Edit task dialog
│   │   └── StatsPanel.java       # Statistics sidebar
│   └── utils/
│       ├── ThemeManager.java     # Theme management
│       └── DateUtils.java        # Date utilities
├── src/main/resources/
│   └── logback.xml               # Logging configuration
├── pom.xml                       # Maven dependencies
└── README.md                     # Documentation
```

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Installation

1. **Clone repository**
```bash
git clone https://github.com/pdericknate/finance-tracker-javafx.git
cd finance-tracker-javafx/todo-list
```

2. **Build project**
```bash
mvn clean install
```

3. **Run application**
```bash
mvn javafx:run
```

## 📖 Usage Guide

### Adding a Task
1. Click **"+ New Task"** button
2. Enter task title (required)
3. Add description (optional)
4. Select priority (High/Medium/Low)
5. Set due date (optional)
6. Click **"Save"**

### Editing a Task
1. Click on a task in the list
2. Click **"Edit"** button
3. Modify task details
4. Click **"Save"**

### Completing a Task
1. Click the **checkbox** next to task
2. Task will move to completed section
3. Changes auto-save

### Deleting a Task
1. Click on a task
2. Click **"Delete"** button
3. Confirm deletion
4. Task is removed

### Filtering Tasks
- **Status Filter**: Select "All", "Active", or "Completed"
- **Priority Filter**: Select "All", "High", "Medium", or "Low"
- **Search**: Type in search box to find tasks

## ⌨️ Keyboard Shortcuts

| Shortcut | Action |
|----------|--------|
| `Ctrl+N` | New task |
| `Ctrl+S` | Save |
| `Delete` | Delete selected task |
| `Ctrl+F` | Search |
| `Ctrl+Q` | Quit |

## 📊 Data Storage

Tasks are stored in `~/.todolist/data/tasks.json`:

```json
[
  {
    "id": 1,
    "title": "Buy groceries",
    "description": "Milk, eggs, bread",
    "priority": "HIGH",
    "dueDate": "2024-05-25",
    "completed": false,
    "createdAt": "2024-05-21T10:30:00",
    "updatedAt": "2024-05-21T10:30:00"
  },
  {
    "id": 2,
    "title": "Complete project",
    "description": "Finish JavaFX application",
    "priority": "HIGH",
    "dueDate": "2024-05-22",
    "completed": true,
    "createdAt": "2024-05-20T09:00:00",
    "updatedAt": "2024-05-21T15:45:00"
  }
]
```

## 🎨 UI Features

### Color Coding
- **High Priority**: 🔴 Red
- **Medium Priority**: 🟡 Yellow
- **Low Priority**: 🟢 Green
- **Overdue**: ⚫ Black with warning
- **Completed**: ✅ Green checkmark

### Statistics Panel
- Total Tasks
- Active Tasks
- Completed Tasks
- Completion Percentage
- Overdue Count
- Priority Breakdown

## 🔒 Data Security

- Local storage only (no server required)
- JSON file encrypted by OS permissions
- Auto-backup on every change
- No sensitive data transmitted

## 🐛 Troubleshooting

### Tasks not saving?
- Check write permissions in home directory
- Ensure `.todolist/data/` folder exists
- Check logs in `logs/` folder

### Application not starting?
- Verify Java 17+ is installed: `java -version`
- Rebuild project: `mvn clean install`
- Check system requirements

### Data file corrupted?
- Backup `.todolist/data/tasks.json`
- Delete the file
- Restart application (creates new file)

## 📝 License

MIT License - See LICENSE file for details

## 🎉 Features Coming Soon

- [ ] Cloud sync with cloud storage
- [ ] Task categories/tags
- [ ] Recurring tasks
- [ ] Task reminders/notifications
- [ ] Dark mode toggle
- [ ] Import/Export (CSV, PDF)
- [ ] Task attachments
- [ ] Collaborative tasks

## 👨‍💻 Contributing

Feel free to fork, modify, and improve this project!

---

**Made with ❤️ using JavaFX**
