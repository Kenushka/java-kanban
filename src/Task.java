import java.util.Objects;

//Базовые задачи
public class Task {
    private String name;
    private String description;
    private int id;

    public Task(String name, String description){
        this.name = name;
        this.description = description;
    }
    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private TaskStatus status = TaskStatus.NEW;

    public TaskStatus getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return  "ID Задачи '" + id + '\'' + ", Название: '" + name + '\'' +
                ", Описание: '" + description + '\'' +
                ", Статус: " + status +
                '}';
    }
}

