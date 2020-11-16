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

public class ShiftPlannerTest {


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

    @After
    public void tearDown() throws SQLException {
        s.execute("DROP TABLE Employees");
    }

}
