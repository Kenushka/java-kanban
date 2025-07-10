//Управление задачами

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TaskManager {
    public static HashMap<Integer, Task> tasks = new HashMap<>();
    public static HashMap<Integer, Epic> epics = new HashMap<>();
    public static HashMap<Integer, Subtask> subtasks = new HashMap<>();
    public int id = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager manager = new TaskManager();

        while (true) {
            manager.printMenu();
            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    manager.getAllTasks();
                    break;
                case "2":
                    manager.deleteAllTasks();
                    System.out.println("Все задачи удалены.");
                    break;
                case "3":
                    System.out.print("Введите ID задачи: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    manager.getTaskById(id);
                    break;
                case "4":
                    System.out.print("Введите тип задачи (task/epic/subtask): ");
                    String type = scanner.nextLine();
                    System.out.print("Название: ");
                    String name = scanner.nextLine();
                    System.out.print("Описание: ");
                    String description = scanner.nextLine();

                    Task newTask;
                    switch (type) {
                        case "task" -> {
                            newTask = new Task(name, description);
                            manager.createTask(newTask);
                        }
                        case "epic" -> {
                            newTask = new Epic(name, description);
                            manager.createTask(newTask);
                        }
                        case "subtask" -> {
                            System.out.print("Введите ID эпика: ");
                            int epicId = Integer.parseInt(scanner.nextLine());
                            Subtask subtask = new Subtask(name, description);
                            subtask.setEpicId(epicId);
                            manager.createTask(subtask);
                        }
                        default -> System.out.println("Неверный тип задачи.");
                    }
                    break;
                case "5":
                    System.out.print("Введите ID задачи для обновления: ");
                    int updateId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Новое имя: ");
                    String newName = scanner.nextLine();
                    System.out.print("Новое описание: ");
                    String newDescription = scanner.nextLine();

                    if (tasks.containsKey(updateId)) {
                        Task task = new Task(newName, newDescription);
                        task.setId(updateId);
                        manager.updateTask(task);
                    } else if (epics.containsKey(updateId)) {
                        Epic epic = new Epic(newName, newDescription);
                        epic.setId(updateId);
                        manager.updateTask(epic);
                    } else if (subtasks.containsKey(updateId)) {
                        Subtask sub = new Subtask(newName, newDescription);
                        sub.setId(updateId);
                        sub.setEpicId(subtasks.get(updateId).getEpicId());
                        manager.updateTask(sub);
                    } else {
                        System.out.println("Задача не найдена.");
                    }
                    break;
                case "6":
                    System.out.print("Введите ID задачи для удаления: ");
                    int deleteId = Integer.parseInt(scanner.nextLine());
                    manager.deleteTaskById(deleteId);
                    break;
                case "7":
                    System.out.print("Введите ID эпика: ");
                    int epicId = Integer.parseInt(scanner.nextLine());
                    Epic epic = epics.get(epicId);
                    if (epic != null) {
                        for (int subtaskId : epic.getSubtaskIds()) {
                            System.out.println(subtasks.get(subtaskId));
                        }
                    } else {
                        System.out.println("Эпик не найден.");
                    }
                    break;
                case "0":
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Неизвестная команда.");
            }
        }


    }

    public int generateId() {
        return id++;
    }

    public void createTask(Task task) {
        int id = generateId();
        task.setId(id);

        if (task instanceof Epic) {
            Epic epic = (Epic) task;
            epic.addSubtaskId(id);
            epics.put(id, epic);

        } else if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            int epicId = subtask.getEpicId();
            Epic parentEpic = epics.get(epicId);

            if (parentEpic != null) {
                subtasks.put(id, subtask);
                parentEpic.getSubtaskIds().add(id);
                updateEpicStatus(parentEpic);
            } else {
                System.out.println("Ошибка: задача с ID " + epicId + " не найден");
            }
        } else {
            tasks.put(id, task);
        }
    }

    public void updateTask(Task task) {
        if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            subtasks.put(subtask.getId(), subtask);
        } else if (task instanceof Epic) {
            Epic epic = (Epic) task;
            epics.put(epic.getId(), epic);
        } else if (task != null) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Ошибка: задача не найдена");
        }
    }

    public void deleteAllTasks() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    public void getTaskById(int id) {
        if (tasks.containsKey(id) || epics.containsKey(id) || subtasks.containsKey(id)) {
            if (tasks.containsKey(id)) {
                System.out.println("Обычная задача: " + tasks.get(id));
            } else if (epics.containsKey(id)) {
                System.out.println("Эпик: " + epics.get(id));
            } else {
                System.out.println("Подзадача: " + subtasks.get(id));
            }
        } else {
            System.out.println("Задача с таким id не найдена");
        }
    }

    public void deleteTaskById(int id) {
        if (tasks.containsKey(id) || epics.containsKey(id) || subtasks.containsKey(id)) {
            if (tasks.containsKey(id)) {
                tasks.remove(id);
            } else if (epics.containsKey(id)) {
                epics.remove(id);

            } else {
                subtasks.remove(id);
            }
            System.out.println("Задача с id " + id + " удалена");
        } else {
            System.out.println("Задача с таким id не найдена");
        }
    }

    public void getAllTasks() {
        for (Task task : tasks.values()) {
            System.out.println("Обычные задачи: ");
            System.out.println(task);
        }
        for (Epic epic : epics.values()) {
            System.out.println("Эпик: " + epic);
            if (epic.getSubtaskIds().isEmpty()) {
                System.out.println("  (подзадач нет)");
            } else {
                for (int subtaskId : epic.getSubtaskIds()) {
                    Subtask subtask = subtasks.get(subtaskId);
                    if (subtask != null) {
                        System.out.println("  Подзадача: " + subtask);
                    }
                }
            }
        }
        if (tasks.isEmpty() && epics.isEmpty()){
            System.out.println("Трекер задач пуст!");
        }
    }

    public void updateEpicStatus(Epic epic) {
        ArrayList<Integer> subtaskIds = epic.getSubtaskIds();

        if (subtaskIds.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (int subtaskId : subtaskIds) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask != null) {
                TaskStatus status = subtask.getStatus();
                if (status != TaskStatus.NEW) {
                    allNew = false;
                }
                if (status != TaskStatus.DONE) {
                    allDone = false;
                }
            }
        }

        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else if (allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    public void printMenu() {
        System.out.println("\nВыберите действие:");
        System.out.println("1 - Получить список всех задач");
        System.out.println("2 - Удалить все задачи");
        System.out.println("3 - Получить задачу по ID");
        System.out.println("4 - Создать задачу");
        System.out.println("5 - Обновить задачу");
        System.out.println("6 - Удалить задачу по ID");
        System.out.println("7 - Получить подзадачи эпика");
        System.out.println("0 - Выход");
    }


}


