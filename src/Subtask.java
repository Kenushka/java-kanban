//Подзадачи эпика

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description) {
        super(name, description);
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "ID подзадачи: " + getId() +
                ", Название: '" + getName() + '\'' +
                ", Описание: '" + getDescription() + '\'' +
                ", Статус: " + getStatus() +
                ", ID Эпика: " + epicId +
                '}';
    }

}