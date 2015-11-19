package multitest.executor;

public class UnitTask implements Runnable {
    private final Unit unit;

    public UnitTask(Unit unit) {
        this.unit = unit;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);

            unit.setMessage("FIND TARGET");
            System.out.println(unit.message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
