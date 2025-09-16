package test;

import history.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefaultReturnsNonNullTaskManager() {
        TaskManager manager = Managers.getDefault();
        assertNotNull(manager);
        assertInstanceOf(TaskManager.class, manager);
    }

    @Test
    void getDefaultHistoryReturnsNonNullHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager);
        assertInstanceOf(HistoryManager.class, historyManager);
    }
}
