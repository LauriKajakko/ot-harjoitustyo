package domain;

import dao.EmployeeDao;
import dao.ShiftDao;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeService {

    private EmployeeDao employeeDao;
    private ShiftDao shiftDao;

    public EmployeeService(EmployeeDao employeeDao, ShiftDao shiftDao) {
        this.employeeDao = employeeDao;
        this.shiftDao = shiftDao;
    }

    public ArrayList<Employee> getAll() {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            employees = employeeDao.getAll();
        } catch (SQLException throwables) {
            // :D
        }
        return employees;
    }

    public String[] getSummary(Employee employee) {
        ArrayList<Shift> shifts = new ArrayList<>();
        try {
            shiftDao.getShiftsByEmployee(employee);
        } catch (SQLException throwables) {

        }
        int minutesWorked = 0;
        for (Shift shift: shifts) {
            String[] s = shift.getFrom().split(":");
            int hours = Integer.getInteger(s[0]);
            int minutes = Integer.getInteger(s[1]);
            minutes += hours * 60;
            minutesWorked += minutes;
        }

        int hours = minutesWorked / 60;
        int minutes = minutesWorked % 60;

        String[] result = new String[1];
        result[0] = hours + ":" + minutes;
        return result;
    }
}
