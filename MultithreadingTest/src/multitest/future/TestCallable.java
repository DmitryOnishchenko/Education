package multitest.future;

import java.util.concurrent.Callable;

public class TestCallable implements Callable<String> {
    private TestObject obj;
    private int d;

    public TestCallable(TestObject obj, int d) {
        this.obj = obj;
        this.d = d;
    }

    @Override
    public String call() throws Exception {
        String target = "";

        for (TestObject testObject : FutureMain.list) {
            int distance = testObject.getDistance();
            if (distance < d) {
                target = testObject.getName() + " | " + distance;
                break;
            }
        }

        return target;
    }
}
