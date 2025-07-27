package model;

import java.time.LocalDate;

public class Task {
    private int id;
    private String title;
    private String description;
    private boolean done;
    private LocalDate dateTime;

    // Constructor đầy đủ
    public Task(int id, String description, String title) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = false;
    }

    public Task(int id, String description, String title, boolean done, LocalDate dateTime) {
        this(id, description, title);
        this.done = done;
        this.dateTime = dateTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return done;
    }

    public String getDescription() {
        return description;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }
}
