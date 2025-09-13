package task;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private int id;
    private TaskStatus status = TaskStatus.NEW;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TaskType getType() {
        return TaskType.TASK;
    }

    @Override
    public String toString() {
        return "ID Задачи: " + id + ", Название: '" + name + "', Описание: '" + description + "', Статус: " + status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
