package Exercise5_TaskManagement;

public class Task {
    int taskId;
    String taskName;
    String status;   // e.g. "Pending", "In Progress", "Done"
    Task next;       // pointer to next node

    public Task(int taskId, String taskName, String status) {
        this.taskId   = taskId;
        this.taskName = taskName;
        this.status   = status;
        this.next     = null;
    }

    @Override
    public String toString() {
        return String.format("Task[id=%d, name=%s, status=%s]",
                taskId, taskName, status);
    }
}
