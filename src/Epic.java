import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void removeSubtaskId(int id) {
        subtaskIds.remove((Integer) id);
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public String toString() {
        return "ID эпика: " + getId() + ", Название: '" +
                getName() + "', Описание: '" + getDescription() +
                "', Статус: " + getStatus();
    }
}
