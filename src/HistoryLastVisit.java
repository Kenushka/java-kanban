import java.util.ArrayList;
import java.util.List;

public class HistoryLastVisit{
    private final List<Task> historyList = new ArrayList<>();

    public void add(Task task){
        historyList.add(task);
        if (historyList.size()>10){
            historyList.removeFirst();
        }
    }

    public List<Task> getHistory(){
        return historyList;
    }

}
