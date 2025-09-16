package history;

import task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        history.add(task);
        int MAX_HISTORY_SIZE = 10;
        if (history.size() > MAX_HISTORY_SIZE) {
            history.removeFirst(); // храним только последние 10
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
