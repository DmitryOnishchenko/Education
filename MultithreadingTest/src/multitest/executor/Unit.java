package multitest.executor;

public class Unit {
    private static int ID;
    private int id;
    public int updates;
    public String name;
    public String message;
    public boolean waiting = true;

    public Unit(String name) {
        this.id = ++ID;
        this.name = name + id;
    }

    public void updateFastTask() {
        updates++;
    }

    public void updateLongTask() {
        if (id == 5 && waiting) {
            waiting = false;
            System.out.println("Create task");
            ExecutorMain.executeLongTask(this);
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        waiting = true;
        this.message = message;
    }

    @Override
    public String toString() {
        return name + " | updates: " + updates + " | message: " + message;
    }
}
