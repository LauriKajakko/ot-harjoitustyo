package dao;

import domain.Employee;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDao {

    //test.db why isn't this working
    //sqlite> CREATE TABLE Employees (id INTEGER PRIMARY KEY, firstname TEXT, lastname TEXT, role TEXT);
    //sqlite> INSERT INTO Employees (firstname, lastname, role) VALUES ('lauri', 'kajakko', 'ceo');

    private Connection db;
    private Statement s;
    private PreparedStatement p;

    public EmployeeDao() throws SQLException {
        Connection db = DriverManager.getConnection("jdbc:sqlite:test.db");
        this.db = db;
    }

    public ArrayList<Employee>  getAll() throws SQLException {
        p = db.prepareStatement("SELECT * FROM Employees");
        ResultSet r = p.executeQuery();

        ArrayList<Employee> employees = new ArrayList<>();
        while(r.next())
            employees.add(new Employee(r.getString("firstname"), r.getString("lastname"), r.getString("role")));

        return employees;
    }

    public void addNew(Employee employee) throws SQLException {
        p = db.prepareStatement("INSERT INTO Employees (firstname, lastname, role) VALUES (?,?,?)");
        p.setString(1, employee.getFirstName());
        p.setString(2, employee.getLastName());
        p.setString(3, employee.getRole());
        p.executeUpdate();
    }

}
