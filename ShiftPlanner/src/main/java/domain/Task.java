package domain;


public class Task implements Comparable<Task> {

    private String name;
    private Shift shift;

    /**
     * Task constructor
     *
     * @param name of the task
     * @param shift of the task
     */
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
    public String toString() {
        return this.name;
    }

    /**
     * Compares tasks and returns 0 if they are the same
     *
     * @param task
     * @return 0 if same -1 if different
     */
    @Override
    public int compareTo(Task task) {
        if (this.name.equals(task.name) && this.shift.compareTo(task.shift) == 0) {
            return 0;
        }
        return -1;
    }
}
