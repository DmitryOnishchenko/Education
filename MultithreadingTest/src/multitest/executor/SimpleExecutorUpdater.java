package multitest.executor;

import java.util.List;

public class SimpleExecutorUpdater extends Thread {
    private List<Unit> list;

    public SimpleExecutorUpdater(List<Unit> list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true) {
            for (Unit unit : list) {
                unit.updateFastTask();
                unit.updateLongTask();
            }

            try {
                System.out.println("End game loop");
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
