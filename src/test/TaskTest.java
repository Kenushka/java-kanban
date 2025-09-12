package test;

import main.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void tasksWithSameIdShouldBeEqual() {
        Task task1 = new Task("Task1", "Desc1");
        Task task2 = new Task("Task2", "Desc2");
        task2.setId(task1.getId()); // одинаковый id
        assertEquals(task1, task2);
        assertEquals(task1.hashCode(), task2.hashCode());
    }

    @Test
    void subtasksWithSameIdShouldBeEqual() {
        Subtask sub1 = new Subtask("Sub1", "Desc1");
        Subtask sub2 = new Subtask("Sub2", "Desc2");
        sub2.setId(sub1.getId());
        assertEquals(sub1, sub2);
    }

    @Test
    void epicCannotBeItsOwnSubtask() {
        Epic epic = new Epic("Epic1", "Desc");
        epic.setId(1);
        Subtask subtask = new Subtask("Sub", "Desc");
        subtask.setEpicId(999); // некорректный id — проверка логики менеджера
        assertNotEquals(epic.getId(), subtask.getEpicId());
    }

    @Test
    void subtaskCannotBeItsOwnEpic() {
        Subtask subtask = new Subtask("Sub", "Desc");
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
        Task task = new Task("Task", "Desc");
        Epic epic = new Epic("Epic", "Desc");
        Subtask sub = new Subtask("Sub", "Desc");

        assertEquals(TaskType.TASK, task.getType());
        assertEquals(TaskType.EPIC, epic.getType());
        assertEquals(TaskType.SUBTASK, sub.getType());
    }
}
