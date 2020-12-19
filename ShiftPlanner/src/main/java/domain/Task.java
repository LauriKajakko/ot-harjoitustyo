package domain;

public class Task implements Comparable<Task> {

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

    @Override
    public int compareTo(Task task) {
        if (this.name.equals(task.name) && this.shift.compareTo(task.shift) == 0) {
            return 0;
        }
        return -1;
    }
}
