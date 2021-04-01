package domain;

import java.util.Objects;

public abstract class Task
{
    private String taskId;
    private String desc;

    public abstract void execute();

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public Task(String taskId, String desc)
    {
        this.taskId=taskId;
        this.desc=desc;
    }
    public String getTaskId() {
        return taskId;
    }

    @Override
    public String toString() {
        return taskId+ " "+desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId.equals(task.taskId) &&
                desc.equals(task.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, desc);
    }
}
