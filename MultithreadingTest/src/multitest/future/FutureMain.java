package multitest.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureMain {
    public static List<TestObject> list = new ArrayList<>();

    public static void main(String[] args) {

        // fill test list
        for (int i = 0; i < 20; i ++) {
            int distance = (int) (100 * Math.random());
            TestObject test = new TestObject(i, distance, "TestName");
            list.add(test);
        }

        SimpleUpdaterFuture updater = new SimpleUpdaterFuture(list);
        updater.start();
    }

    public static void findTargetFor(TestObject obj, int i) throws ExecutionException, InterruptedException {
        Callable<String> callable = new TestCallable(obj, i);
        FutureTask<String> task = new FutureTask<>(callable);
        new Thread(task).start();

        String target = task.get();
        obj.setTarget(target);
        System.out.println(obj + " find " + target);
    }
}
