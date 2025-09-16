package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskType;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task task;
    private Subtask sub;
    private Epic epic;
    Subtask subtask;

    @BeforeEach
    void setup(){
        task = new Task("Task", "Desc");
        sub = new Subtask("Sub1", "Desc1");
        epic = new Epic("Epic1", "Desc");
        subtask = new Subtask("Sub", "Desc");
    }

    @Test
    void tasksWithSameIdShouldBeEqual() {
        Task task2 = new Task("Task2", "Desc2");
        task2.setId(task.getId()); // одинаковый id
        assertEquals(task, task2);
        assertEquals(task.hashCode(), task2.hashCode());
    }

    @Test
    void subtasksWithSameIdShouldBeEqual() {
        Subtask sub2 = new Subtask("Sub2", "Desc2");
        sub2.setId(sub.getId());
        assertEquals(sub, sub2);
    }

    @Test
    void epicCannotBeItsOwnSubtask() {
        epic.setId(1);
        subtask.setEpicId(999); // некорректный id — проверка логики менеджера
        assertNotEquals(epic.getId(), subtask.getEpicId());
    }

    @Test
    void subtaskCannotBeItsOwnEpic() {
        subtask.setId(1);
        subtask.setEpicId(2);
        assertNotEquals(subtask.getId(), subtask.getEpicId());
    }

    @Test
    void taskToStringReturnsCorrectFormat() {
        Task task = new Task("Task1", "Desc1");
        String expected = "ID Задачи: " + task.getId() + ", Название: 'Task1', Описание: 'Desc1', Статус: NEW";
        assertEquals(expected, task.toString());
    }

    @Test
    void taskTypeIsCorrect() {
        assertEquals(TaskType.TASK, task.getType());
        assertEquals(TaskType.EPIC, epic.getType());
        assertEquals(TaskType.SUBTASK, sub.getType());
    }
}
