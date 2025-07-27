package view;

import javafx.scene.layout.VBox;

public class TaskNode {
    private VBox taskVBox;
    private TaskNode next;

    public TaskNode(VBox taskVBox){
        this.taskVBox = taskVBox;
        this.next = null;
    }

    public VBox getTaskVBox() {
        return taskVBox;
    }

    public TaskNode getNext() {
        return next;
    }

    public void setTaskVBox(VBox taskVBox) {
        this.taskVBox = taskVBox;
    }

    public void setNext(TaskNode next) {
        this.next = next;
    }
}
