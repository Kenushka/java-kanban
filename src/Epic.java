import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public void addSubtaskId(int id) {
        subtaskIds.add(id);
    }

    public void removeSubtaskId(int id) {
        subtaskIds.remove(Integer.valueOf(id)); // важно: удаление по значению, а не индексу
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public String toString() {
        return "ID эпика: '" + getId() +
                ", Название: '" + getName() + '\'' +
                ", Описание: '" + getDescription() + '\'' +
                ", Статус: " + getStatus() +
                '}';
    }

}
