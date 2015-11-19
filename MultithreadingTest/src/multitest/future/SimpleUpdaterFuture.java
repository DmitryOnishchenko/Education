package multitest.future;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SimpleUpdaterFuture extends Thread {
    private List<TestObject> list;

    public SimpleUpdaterFuture(List<TestObject> list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true) {
            try {
                for (int i = 0; i < list.size(); i ++) {
                    TestObject obj = list.get(i);
                    obj.update();

                    if (i % 5 == 0) {
                        FutureMain.findTargetFor(obj, 20);
                    }
                }

                System.out.println("Life cycle ended");

                for (TestObject test : list) {
                    test.setDistance((int) (100 * Math.random()));
                }

                Thread.sleep(1000);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
