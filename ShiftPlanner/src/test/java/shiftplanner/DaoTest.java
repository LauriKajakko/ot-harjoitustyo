package shiftplanner;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import dao.EmployeeDao;
import domain.Employee;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

public class DaoTest {


    private Statement s;
    private PreparedStatement p;
    private Connection testdb;

    @Before
    public void setUp() throws SQLException{
        testdb = DriverManager.getConnection("jdbc:sqlite:test.db");
        s = testdb.createStatement();

        s.execute("BEGIN TRANSACTION");
        s.execute("CREATE TABLE Employees (id INTEGER PRIMARY KEY, firstname TEXT, lastname TEXT, role TEXT)");
        s.execute("COMMIT;");

        s.execute("BEGIN TRANSACTION");
        s.execute("INSERT INTO Employees (firstname, lastname, role) VALUES ('lauri', 'kajakko', 'ceo')");
        s.execute("INSERT INTO Employees (firstname, lastname, role) VALUES ('matti', 'meikalainen', 'cto')");
        s.execute("COMMIT;");

        s.execute("BEGIN TRANSACTION");
        s.execute("CREATE TABLE Shifts (id INTEGER PRIMARY KEY, fromtime TEXT, totime TEXT, date TEXT,  employee_id INTEGER)");
        s.execute("COMMIT;");

        s.execute("BEGIN TRANSACTION");
        s.execute("INSERT INTO Shifts (fromtime, totime, date, employee_id) VALUES ('08:00:00', '16:00:00', '18.11.2020', 1)");
        s.execute("INSERT INTO Shifts (fromtime, totime, date, employee_id) VALUES ('08:00:00', '16:00:00', '18.11.2020', 2)");
        s.execute("INSERT INTO Shifts (fromtime, totime, date, employee_id) VALUES ('22:00:00', '06:00:00', '19.11.2020', 1)");
        s.execute("INSERT INTO Shifts (fromtime, totime, date, employee_id) VALUES ('08:00:00', '16:00:00', '29.11.2020', 1)");
        s.execute("INSERT INTO Shifts (fromtime, totime, date, employee_id) VALUES ('08:00:00', '16:00:00', '01.12.2020', 2)");
        s.execute("COMMIT;");
    }

    @Test
    public void rightEmployeesAreReturned() throws SQLException{
        EmployeeDao dao = new EmployeeDao(testdb);
        ArrayList<Employee> expectedList = new ArrayList<>();
        expectedList.add(new Employee("lauri", "kajakko", "ceo"));
        expectedList.add(new Employee("matti", "meikalainen", "cto"));

        ArrayList<Employee> resultList = dao.getAll();

        assertEquals(expectedList.get(0).getFirstName(), resultList.get(0).getFirstName());
        assertEquals(expectedList.get(1).getFirstName(), resultList.get(1).getFirstName());
    }

    @Test
    public void addingNewEmployeesWork() throws SQLException{
        EmployeeDao dao = new EmployeeDao(testdb);
        Employee newEmployee = new Employee("mutti", "polpa", "manager");
        dao.addNew(newEmployee);

        assertEquals(newEmployee.getFirstName() ,dao.getAll().get(2).getFirstName());
    }

    @After
    public void tearDown() throws SQLException {
        s.execute("DROP TABLE Employees");
    }

}
