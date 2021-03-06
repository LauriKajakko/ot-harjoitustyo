package domain;

import java.util.ArrayList;

public class Shift implements Comparable<Shift> {

    private String from;
    private String to;
    private String date;
    private Employee employee;

    /**
     * Shift constructor
     *
     * @param from starting time as hh:mm:ss (hours:minutes:seconds)
     * @param to   leaving time as hh:mm:ss (hours:minutes:seconds)
     * @param date day of the shift as dd:mm:yyyy (days:months:years)
     * @param employee who is working this shift
     */
    public Shift(String from, String to, String date, Employee employee)  {
        this.from = from;
        this.to = to;
        this.date = date;
        this.employee = employee;
    }

    @Override
    public String toString() {
        return date + " " + " " + from + "-" + to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDate() {
        return date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public int compareTo(Shift shift) {
        if (this.getTo().equals(shift.getTo()) && this.getFrom().equals(shift.getFrom()) && this.getDate().equals(shift.getDate()) && this.getEmployee().equals(shift.getEmployee())) {
            return 0;
        }
        return -1;
    }
}