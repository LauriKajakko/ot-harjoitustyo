package domain;

import java.util.ArrayList;

public class Task {

    private String name;
    private Shift shift;
    private boolean done;

    public Task(String name, Shift shift, boolean done) {
        this.name = name;
        this.shift = shift;
        this.done = done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public boolean isDone() {
        return done;
    }
}
