package com.todolist.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    public static String getRelativeDate(LocalDate date) {
        if (date == null) {
            return "No date";
        }

        LocalDate today = LocalDate.now();
        long daysUntil = ChronoUnit.DAYS.between(today, date);

        if (daysUntil == 0) {
            return "Today";
        } else if (daysUntil == 1) {
            return "Tomorrow";
        } else if (daysUntil == -1) {
            return "Yesterday";
        } else if (daysUntil < 0) {
            return daysUntil + " days ago";
        } else if (daysUntil <= 7) {
            return "In " + daysUntil + " days";
        } else {
            return date.toString();
        }
    }

    public static boolean isOverdue(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.isBefore(LocalDate.now());
    }

    public static boolean isDueToday(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.isEqual(LocalDate.now());
    }

    public static String getFormattedDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"));
    }
}
