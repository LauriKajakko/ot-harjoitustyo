package domain;

import java.util.*;

public class Shift {

    private String from;
    private String to;
    private String date;
    private String role;

    /**
     * @param from starting time as hh:mm (hours:minutes)
     * @param to   leaving time as hh:mm (hours:minutes)
     * @param date day of the shift as dd:mm:yyyy (days:months:years)
     * @param role what role is the employee working
     */
    public Shift(String from, String to, String date, String role) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.role = role;
    }

}