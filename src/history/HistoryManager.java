package history;

import task.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);          // помечает задачу как просмотренную
    List<Task> getHistory();      // возвращает список последних задач
}
