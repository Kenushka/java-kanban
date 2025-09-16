package manager;

import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        // 2 обычные задачи
        Task task1 = new Task("Задача 1", "Описание 1");
        Task task2 = new Task("Задача 2", "Описание 2");
        manager.createTask(task1);
        manager.createTask(task2);

        // Эпик 1 с 2 подзадачами
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        manager.createTask(epic1);

        Subtask sub1 = new Subtask("Подзадача 1", "Описание подзадачи 1");
        sub1.setEpicId(epic1.getId());
        manager.createTask(sub1);

        Subtask sub2 = new Subtask("Подзадача 2", "Описание подзадачи 2");
        sub2.setEpicId(epic1.getId());
        manager.createTask(sub2);

        // Эпик 2 с 1 подзадачей
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        manager.createTask(epic2);

        Subtask sub3 = new Subtask("Подзадача 3", "Описание подзадачи 3");
        sub3.setEpicId(epic2.getId());
        manager.createTask(sub3);

        System.out.println("\n--- Все задачи ---");
        manager.getAllTasks().forEach(System.out::println);

        System.out.println("\n--- Все эпики ---");
        manager.getAllEpics().forEach(System.out::println);

        System.out.println("\n--- Все подзадачи ---");
        manager.getAllSubtasks().forEach(System.out::println);

        System.out.println("\n--- Изменение статусов ---");
        task1.setStatus(TaskStatus.IN_PROGRESS);
        sub1.setStatus(TaskStatus.DONE);
        sub2.setStatus(TaskStatus.IN_PROGRESS);
        sub3.setStatus(TaskStatus.DONE);

        manager.updateTask(task1);
        manager.updateTask(sub1);
        manager.updateTask(sub2);
        manager.updateTask(sub3);

        System.out.println("\n--- После обновления статусов ---");
        manager.getAllTasks().forEach(System.out::println);
        manager.getAllEpics().forEach(System.out::println);
        manager.getAllSubtasks().forEach(System.out::println);

        System.out.println("\n--- Удаление задачи и эпика ---");
        manager.deleteTaskById(task2.getId());
        manager.deleteTaskById(epic1.getId());

        System.out.println("\n--- После удаления ---");
        manager.getAllTasks().forEach(System.out::println);
        manager.getAllEpics().forEach(System.out::println);
        manager.getAllSubtasks().forEach(System.out::println);
    }
}
