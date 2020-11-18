package domain;

public class Shift {

    private String from;
    private String to;
    private String date;
    private Employee employee;

    /**
     * @param from starting time as hh:mm:ss (hours:minutes:seconds)
     * @param to   leaving time as hh:mm:ss (hours:minutes:seconds)
     * @param date day of the shift as dd:mm:yyyy (days:months:years)
     * @param employee who is working this shift
     */
    public Shift(String from, String to, String date, Employee employee) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.employee = employee;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}