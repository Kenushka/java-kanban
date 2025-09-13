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
        assertTrue(manager instanceof TaskManager);
    }

    @Test
    void getDefaultHistoryReturnsNonNullHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager);
        assertTrue(historyManager instanceof HistoryManager);
    }
}
