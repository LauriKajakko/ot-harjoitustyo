package domain;

public class Task {

    private String name;
    private Shift shift;

    public Task(String name, Shift shift) {
        this.name = name;
        this.shift = shift;
    }

    public String getName() {
        return name;
    }

    public Shift getShift() {
        return shift;
    }

}
