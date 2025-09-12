package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private int id = 1;


    private int generateId() {
        return id++;
    }

    @Override
    public void createTask(Task task) {
        int id = generateId();
        task.setId(id);

        switch (task.getType()) {
            case TASK -> tasks.put(id, task);
            case EPIC -> epics.put(id, (Epic) task);
            case SUBTASK -> {
                Subtask subtask = (Subtask) task;
                Epic epic = epics.get(subtask.getEpicId());
                if (epic != null) {
                    subtasks.put(id, subtask);
                    epic.getSubtaskIds().add(id);
                    updateEpicStatus(epic);
                } else {
                    System.out.println("Ошибка: эпик с ID " + subtask.getEpicId() + " не найден.");
                }
            }
        }
    }

    @Override
    public void updateTask(Task task) {
        int id = task.getId();
        switch (task.getType()) {
            case TASK -> tasks.put(id, task);
            case EPIC -> epics.put(id, (Epic) task);
            case SUBTASK -> {
                Subtask subtask = (Subtask) task;
                subtasks.put(id, subtask);
                Epic epic = epics.get(subtask.getEpicId());
                if (epic != null) {
                    updateEpicStatus(epic);
                }
            }
        }
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        for (Epic epic : epics.values()) {
            for (int subId : epic.getSubtaskIds()) {
                subtasks.remove(subId);
            }
        }
        epics.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            updateEpicStatus(epic);
        }
        subtasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null){
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null){
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public void deleteTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else if (epics.containsKey(id)) {
            Epic epic = epics.remove(id);
            if (epic != null) {
                for (int subId : epic.getSubtaskIds()) {
                    subtasks.remove(subId);
                }
            }
        } else if (subtasks.containsKey(id)) {
            Subtask subtask = subtasks.remove(id);
            if (subtask != null) {
                Epic epic = epics.get(subtask.getEpicId());
                if (epic != null) {
                    epic.removeSubtaskId(id);
                    updateEpicStatus(epic);
                }
            }
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    private void updateEpicStatus(Epic epic) {
        List<Integer> subtaskIds = epic.getSubtaskIds();

        if (subtaskIds.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (int subId : subtaskIds) {
            Subtask sub = subtasks.get(subId);
            if (sub != null) {
                if (sub.getStatus() != TaskStatus.NEW) allNew = false;
                if (sub.getStatus() != TaskStatus.DONE) allDone = false;
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

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}
