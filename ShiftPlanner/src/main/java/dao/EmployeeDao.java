package dao;

import domain.Employee;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDao {


    private Connection db;
    private Statement s;
    private PreparedStatement p;


    /**
     *
     * @param db Test Database connection
     * @throws SQLException
     */
    public EmployeeDao(Connection db) throws SQLException {
        this.db = db;
    }

    /**
     *
     * @return all employees in database
     * @throws SQLException
     */
    public ArrayList<Employee>  getAll() throws SQLException {
        p = db.prepareStatement("SELECT * FROM Employees");
        ResultSet r = p.executeQuery();

        ArrayList<Employee> employees = new ArrayList<>();
        while (r.next()) {
            employees.add(new Employee(r.getString("firstname"), r.getString("lastname"), r.getString("role")));
        }
        return employees;
    }

    public Employee getByName(String first, String last) throws SQLException {
        p = db.prepareStatement("SELECT * FROM Employees WHERE firstname=(?) AND lastname=(?)");
        p.setString(1, first);
        p.setString(2, last);
        ResultSet r = p.executeQuery();

        Employee employee = new Employee(r.getString("firstname"), r.getString("lastname"), r.getString("role"));

        return employee;
    }

    /**
     *
     * Adds employee to database
     *
     * @param employee employee to be added
     * @throws SQLException
     */
    public void addNew(Employee employee) throws SQLException {
        p = db.prepareStatement("INSERT INTO Employees (firstname, lastname, role) VALUES (?,?,?)");
        p.setString(1, employee.getFirstName());
        p.setString(2, employee.getLastName());
        p.setString(3, employee.getRole());
        p.executeUpdate();
    }

}
