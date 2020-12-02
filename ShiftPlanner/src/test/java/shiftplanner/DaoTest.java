package shiftplanner;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import dao.Database;
import dao.EmployeeDao;
import dao.ShiftDao;
import dao.TaskDao;
import domain.Employee;
import domain.Shift;
import domain.Task;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

public class DaoTest {


    private Statement s;
    private PreparedStatement p;
    private Connection testdb;
    Database db;

    @Before
    public void setUp() throws SQLException{
        db = new Database("test.db");
        testdb = db.connect();

        s = testdb.createStatement();

        s.execute("BEGIN TRANSACTION");
        s.execute("INSERT INTO Shifts (fromtime, totime, date, employee_id) VALUES ('08:00:00', '16:00:00', '18.11.2020', 1)");
        s.execute("INSERT INTO Shifts (fromtime, totime, date, employee_id) VALUES ('08:00:00', '16:00:00', '18.11.2020', 2)");
        s.execute("INSERT INTO Shifts (fromtime, totime, date, employee_id) VALUES ('22:00:00', '06:00:00', '19.11.2020', 1)");
        s.execute("INSERT INTO Shifts (fromtime, totime, date, employee_id) VALUES ('08:00:00', '16:00:00', '29.11.2020', 1)");
        s.execute("INSERT INTO Shifts (fromtime, totime, date, employee_id) VALUES ('08:00:00', '16:00:00', '01.12.2020', 2)");
        s.execute("COMMIT;");


        s.execute("BEGIN TRANSACTION");
        s.execute("INSERT INTO Employees (firstname, lastname, role) VALUES ('lauri', 'kajakko', 'ceo')");
        s.execute("INSERT INTO Employees (firstname, lastname, role) VALUES ('matti', 'meikalainen', 'cto')");
        s.execute("COMMIT;");

        s.execute("BEGIN TRANSACTION");
        s.execute("INSERT INTO Tasks (name, done, shift_id) VALUES ('refactor code', 1, 1)");
        s.execute("INSERT INTO Tasks (name, done, shift_id) VALUES ('write code', 1, 1)");
        s.execute("INSERT INTO Tasks (name, done, shift_id) VALUES ('refactor more code', 0, 1)");
        s.execute("COMMIT;");
    }

    @Test
    public void rightTasksAreReturnedWithGetTasksByShift() throws SQLException {
        TaskDao dao = new TaskDao(testdb);
        ShiftDao sdao = new ShiftDao(testdb);
        EmployeeDao edao = new EmployeeDao(testdb);

        ArrayList<Shift> shifts = sdao.getShiftsByEmployee(edao.getByName("lauri", "kajakko"));
        ArrayList<Task> tasks = dao.getTasksByShift(shifts.get(0));

        assertEquals("refactor code", tasks.get(0).getName());
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

        for(Employee e : dao.getAll())
            System.out.println(e.getFirstName());

        assertEquals(newEmployee.getFirstName() ,dao.getAll().get(2).getFirstName());
    }

    @Test
    public void rightShiftsAreReturnedByEmployee() throws SQLException {
        ShiftDao dao = new ShiftDao(testdb);
        EmployeeDao employeeDao = new EmployeeDao(testdb);
        Employee e = employeeDao.getAll().get(0);

        ArrayList<Shift> shifts = dao.getShiftsByEmployee(e);
        assertEquals("08:00:00", shifts.get(0).getFrom());
        assertEquals("16:00:00", shifts.get(0).getTo());
        assertEquals("18.11.2020", shifts.get(0).getDate());
    }

    @Test
    public void addShiftWorks() throws SQLException {
        ShiftDao dao = new ShiftDao(testdb);
        EmployeeDao edao = new EmployeeDao(testdb);
        Employee e = edao.getByName("lauri", "kajakko");
        Shift shift = new Shift("11:00", "12:00", "12/12/2020", e);
        dao.addShift(shift);
        Shift testShift = dao.getShiftsByEmployee(e).get(3);
        assertEquals(shift.getFrom()+shift.getTo()+shift.getDate()+shift.getEmployee(), testShift.getFrom()+testShift.getTo()+testShift.getDate()+testShift.getEmployee());
    }



    @After
    public void tearDown() throws SQLException {
        db.delete();
    }



}
