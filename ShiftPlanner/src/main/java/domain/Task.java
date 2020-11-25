package domain;

import java.util.ArrayList;

public class Task {

    private String name;
    private ArrayList<Shift> shifts;
    private boolean done;

    public Task(String name, ArrayList<Shift> shifts, boolean done) {
        this.name = name;
        this.shifts = shifts;
        this.done = done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Shift> getShifts() {
        return shifts;
    }

    public boolean isDone() {
        return done;
    }
}
