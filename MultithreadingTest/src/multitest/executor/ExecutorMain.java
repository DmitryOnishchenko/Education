package multitest.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorMain {
    public static List<Unit> list = new ArrayList<>();
    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            Unit unit = new Unit("TestName");
            list.add(unit);
        }


        SimpleExecutorUpdater updater = new SimpleExecutorUpdater(list);
        updater.start();
    }


    public static void executeLongTask(Unit unit) {
        UnitTask task = new UnitTask(unit);
        executor.submit(task);
    }
}
