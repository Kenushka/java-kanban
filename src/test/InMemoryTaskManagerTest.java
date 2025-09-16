package test;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private TaskManager manager;
    private Task task;
    private Epic epic;
    private Subtask sub;

    @BeforeEach
    void setup() {
        manager = Managers.getDefault();
        task = new Task("Task", "Desc");
        epic = new Epic("Epic", "Desc");
        sub = new Subtask("Sub", "Desc");
    }

    @Test
    void addAndRetrieveTasks() {
        manager.createTask(task);
        manager.createTask(epic);
        sub.setEpicId(epic.getId());
        manager.createTask(sub);

        assertTrue(manager.getAllTasks().contains(task));
        assertTrue(manager.getAllEpics().contains(epic));
        assertTrue(manager.getAllSubtasks().contains(sub));

        assertEquals(task, manager.getTaskById(task.getId()));
        assertEquals(epic, manager.getEpicById(epic.getId()));
        assertEquals(sub, manager.getSubtaskById(sub.getId()));
    }

    @Test
    void deleteAllTasksEpicsSubtasks() {
        manager.createTask(task);
        manager.createTask(epic);
        sub.setEpicId(epic.getId());
        manager.createTask(sub);

        manager.deleteAllTasks();
        assertTrue(manager.getAllTasks().isEmpty());

        manager.deleteAllSubtasks();
        assertTrue(manager.getAllSubtasks().isEmpty());

        manager.deleteAllEpics();
        assertTrue(manager.getAllEpics().isEmpty());
    }

    @Test
    void deleteById() {
        manager.createTask(task);
        manager.createTask(epic);
        sub.setEpicId(epic.getId());
        manager.createTask(sub);

        manager.deleteTaskById(task.getId());
        assertFalse(manager.getAllTasks().contains(task));

        manager.deleteTaskById(epic.getId());
        assertFalse(manager.getAllEpics().contains(epic));
        assertTrue(manager.getAllSubtasks().isEmpty());
    }

    @Test
    void updateTaskStatus() {
        manager.createTask(task);
        task.setStatus(TaskStatus.DONE);
        manager.updateTask(task);

        assertEquals(TaskStatus.DONE, manager.getTaskById(task.getId()).getStatus());
    }

    @Test
    void historyLimitAndTypes() {
        manager.createTask(epic);

        for (int i = 0; i < 15; i++) {
            Task task = new Task("Task" + i, "Desc" + i);
            manager.createTask(task);
            manager.getTaskById(task.getId());
        }

        manager.getEpicById(epic.getId());

        List<Task> history = manager.getHistory();
        assertTrue(history.size() <= 10);
        assertTrue(history.contains(epic));
    }

    @Test
    void addSubtaskWithNonExistingEpicDoesNotThrow() {
        Subtask subtask = new Subtask("Sub", "Desc");
        subtask.setEpicId(999); // несуществующий эпик
        manager.createTask(subtask);
        assertTrue(manager.getAllSubtasks().isEmpty());
    }

    @Test
    void deleteNonExistingIdDoesNotThrow() {
        assertDoesNotThrow(() -> manager.deleteTaskById(999));
    }

    @Test
    void getAllOnEmptyManagerReturnsEmptyLists() {
        assertTrue(manager.getAllTasks().isEmpty());
        assertTrue(manager.getAllEpics().isEmpty());
        assertTrue(manager.getAllSubtasks().isEmpty());
    }

    @Test
    void idsDoNotConflict() {
        Task task1 = new Task("Task1", "Desc");
        task1.setId(100); // вручную
        manager.createTask(task1);

        Task task2 = new Task("Task2", "Desc");
        manager.createTask(task2); // генерируемый id

        assertNotEquals(task1.getId(), task2.getId());
    }

    @Test
    void taskRemainsImmutableAfterAdding() {        task.setStatus(TaskStatus.NEW);
        manager.createTask(task);

        assertEquals("Task", task.getName());
        assertEquals("Desc", task.getDescription());
        assertEquals(TaskStatus.NEW, task.getStatus());
    }
}
