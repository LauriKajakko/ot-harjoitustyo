package services;

import dao.EmployeeDao;
import dao.ShiftDao;
import domain.Employee;
import domain.Shift;

import java.io.StringBufferInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class EmployeeService {

    private EmployeeDao employeeDao;
    private ShiftDao shiftDao;

    public EmployeeService(EmployeeDao employeeDao, ShiftDao shiftDao) {
        this.employeeDao = employeeDao;
        this.shiftDao = shiftDao;
    }

    /**
     * Gets all employees
     *
     * @return all employees as ArrayList
     */
    public ArrayList<Employee> getAll() {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            employees = employeeDao.getAll();
        } catch (Exception e) {
            // :D
        }
        return employees;
    }

    /**
     *
     * Gets latest added workdays of selected employee. Returns a pair of day and worked minutes in a table.
     * If there is less workdays than queried then it return empty strings for remaining indexes.
     *
     * @param employee whos
     * @param days
     * @return String[][] where first is days and then at index 0 the date and index 1 the minutes worked
     */
    public String[][] getLastWorkDays(Employee employee, int days) {
        ArrayList<Shift> shifts = new ArrayList<>();
        try {
            shifts = shiftDao.getShiftsByEmployee(employee);
            Collections.reverse(shifts);
        } catch (SQLException throwables) {
        }
        //First minutes then day
        String[][] result = new String[days][2];

        for (int i = 0; i < days; i++) {
            if (shifts.size()<=i) {
                result[i][0] = "0";
                result[i][1] = "";
                continue;
            }
            int hours = 0;
            int minutes = 0;
            String[] from = shifts.get(i).getFrom().split(":");
            String[] to = shifts.get(i).getTo().split(":");
            if(from[0] == to[0]){
                minutes = Integer.parseInt(to[1]) - Integer.parseInt(from[1]);
            } else {
                hours = Integer.parseInt(to[0]) - Integer.parseInt(from[0]);
                minutes = hours * 60 + Integer.parseInt(to[1]) - Integer.parseInt(from[1]);
            }
            String date = shifts.get(i).getDate();
            result[i][0] = Integer.toString(minutes);
            result[i][1] = date;
        }

        return result;
    }

    /**
     * Adds an employee to database
     *
     * @param employee to add
     */
    public void addEmployee(Employee employee) {
        try {
            employeeDao.addNew(employee);
        } catch (SQLException throwables) {

        }
    }

}
