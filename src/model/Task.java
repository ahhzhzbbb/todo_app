package model;

public class Task {
    private int id;
    private String title;
    private String description;
    private boolean done;

    public Task(String description) {
        this(description, "Untitled");
    }

    // Constructor đầy đủ
    public Task(String description, String title) {
        this.title = title;
        this.description = description;
        this.done = false;
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
}
