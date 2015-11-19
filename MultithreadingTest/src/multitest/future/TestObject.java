package multitest.future;

public class TestObject {
    private int id;
    private int distance;
    private String name;
    private String target = "";
    private int updates;

    public TestObject(int id, int distance, String name) {
        this.id = id;
        this.distance = distance;
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void update() {
        updates++;
    }

    public int getUpdates() {
        return updates;
    }

    public void setUpdates(int updates) {
        this.updates = updates;
    }

    @Override
    public String toString() {
        return id + " | " + name;
    }
}
