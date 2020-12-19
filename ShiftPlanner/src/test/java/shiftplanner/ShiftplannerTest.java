package shiftplanner;

import dao.Database;
import dao.EmployeeDao;
import dao.ShiftDao;
import dao.TaskDao;
import org.junit.*;
import domain.Employee;
import domain.Shift;
import domain.Task;
import org.junit.Before;
import services.EmployeeService;
import services.ShiftService;
import services.TaskService;
import utils.Conversions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class ShiftplannerTest {

    Database db;
    EmployeeDao employeeDao;
    ShiftDao shiftDao;
    TaskDao taskDao;

    EmployeeService employeeService;
    ShiftService shiftService;
    TaskService taskService;

    Employee employeeOne;

    @Before
    public void setUp() throws SQLException {
        employeeOne = new Employee("lauri", "kajakko", "ceo");

        db = new Database("test2.db");
        Connection connection = db.connect();

        employeeDao = new EmployeeDao(connection);
        shiftDao = new ShiftDao(connection);
        taskDao = new TaskDao(connection);

        this.employeeService = new EmployeeService(employeeDao, shiftDao);
        this.shiftService = new ShiftService(shiftDao);
        this.taskService = new TaskService(taskDao);

        this.employeeService.addEmployee(employeeOne);
        this.shiftService.addShift(new Shift("8:00" , "16:00", "02-02-2020", employeeOne));
    }

    @Test
    public void gettingEmployeesWorks() {
        ArrayList<Employee> l = employeeService.getAll();
        assertEquals(l.get(0).toString(), employeeOne.toString());
    }

    @Test
    public void gettingShiftsByEmployeeWorks() {
        ArrayList<Shift> l = shiftService.getShiftsByEmployee(employeeOne);
        assertEquals(l.get(0).toString(), new Shift("8:00" , "16:00", "02-02-2020", employeeOne).toString());
    }

    @Test
    public void addingEmployeesWorks() {
        employeeService.addEmployee(new Employee("essi", "esimerkki", "cto"));
        ArrayList<Employee> l = employeeService.getAll();
        assertEquals(l.get(1).toString(), new Employee("essi", "esimerkki", "cto").toString());
    }

    @Test
    public void addingShiftsWorks() {
        shiftService.addShift(new Shift("8:00" , "16:00", "02-03-2020", employeeOne));
        ArrayList<Shift> l = shiftService.getShiftsByEmployee(employeeOne);
        assertEquals(l.get(1).toString(), new Shift("8:00" , "16:00", "02-03-2020", employeeOne).toString());
    }

    @Test
    public void deletingShiftWorks() {
        ArrayList<Shift> expected = shiftService.getShiftsByEmployee(employeeOne);
        Shift shiftToAdd = new Shift("11:00", "12:00", "2020-20-12", employeeOne);
        shiftService.addShift(shiftToAdd);
        shiftService.deleteShift(shiftToAdd);
        ArrayList<Shift> result = shiftService.getShiftsByEmployee(employeeOne);

        assertEquals(expected.size(), result.size());
        assertEquals(0, expected.get(expected.size() - 1).compareTo(result.get(result.size() - 1)));
    }


    @Test
    public void editingShiftWorks() {
        ArrayList<Shift> first = shiftService.getShiftsByEmployee(employeeOne);
        Shift s = first.get(0);
        shiftService.editShift(s, "00:00", "12:34");
        Shift result = shiftService.getShiftsByEmployee(employeeOne).get(0);

        assertEquals("00:00", result.getFrom());
        assertEquals("12:34", result.getTo());
    }

    @Test
    public void addingTaskWorks() {
        Task exp = new Task("clean up", new Shift("8:00" , "16:00", "02-02-2020", employeeOne));
        taskService.addTask(exp);
        Task res = taskService.getTasksByShift(new Shift("8:00" , "16:00", "02-02-2020", employeeOne)).get(0);
        assertEquals(0, exp.compareTo(res));
    }

    @Test
    public void deletingTaskWorks() {
        Task exp = new Task("clean up", new Shift("8:00" , "16:00", "02-02-2020", employeeOne));
        taskService.addTask(exp);
        assertEquals(1, taskService.getTasksByShift(new Shift("8:00", "16:00", "02-02-2020", employeeOne)).size());
        taskService.deleteTask(exp);
        assertEquals(0, taskService.getTasksByShift(new Shift("8:00", "16:00", "02-02-2020", employeeOne)).size());
    }

    @Test
    public void deletingAllShiftsTasksWorks() {
        Shift s = new Shift("8:00" , "16:00", "02-02-2020", employeeOne);
        Task exp = new Task("clean up", s);
        taskService.addTask(exp);
        taskService.addTask(exp);
        taskService.addTask(exp);
        taskService.deleteTasksByShift(s);
        assertTrue(taskService.getTasksByShift(s).isEmpty());

    }

    @Test
    public void comparingEmployeesReturnsZeroOrMinusOne() {
        Employee e = new Employee("lauri", "kajakko", "ceo");
        assertEquals(0, e.compareTo(employeeOne));
        Employee e2 = new Employee("lauri", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "ceo");
        assertEquals(e2.compareTo(employeeOne), -1);
    }

    @Test
    public void minutesToHoursWorks() {
        Conversions c = new Conversions();
        assertEquals(2, c.minutesToHours("120"), 0.0);
        assertEquals(1.5, c.minutesToHours("90"), 0.0);
    }

    @Test
    public void setEmployeeWorks() {
        Employee e = new Employee("erkki", "edimeksei", "cleaner");
        Employee e2 = new Employee("erkki", "esimerkki", "cleaner");
        Shift s = new Shift("11:11", "12:12", "01-01-2020", e);
        s.setEmployee(e2);
        assertEquals(0, s.getEmployee().compareTo(e2));
    }

    @Test
    public void getLastWorkDaysReturnLatestAdded() {
        String[][] res = employeeService.getLastWorkDays(employeeOne, 6);
        assertEquals(res[0][0], Integer.toString(8 * 60));
    }

    @Test
    public void comparisonsWorkWithEmployee() {
        Employee e = new Employee("a", "kajakko", "ceo");
        Employee e2 = new Employee("lauri", "a", "ceo");
        Employee e3 = new Employee("lauri", "kajakko", "a");

        assertEquals(e.compareTo(employeeOne), -1);
        assertEquals(e2.compareTo(employeeOne), -1);
        assertEquals(e3.compareTo(employeeOne), -1);
    }

    @Test
    public void comparisonsWorkWithTask() {
        Shift s = new Shift("18:00", "19:00", "02-02-2012", employeeOne);
        Shift s2 = new Shift("10:00", "19:00", "02-02-2012", employeeOne);

        Task t = new Task("do stuff", s);
        Task t2 = new Task("do other stuff", s);
        Task t3 = new Task("do stuff", s2);
        assertEquals(t.compareTo(t2), -1);
        assertEquals(t.compareTo(t3), -1);
    }

    @Test
    public void comparisonsWorkWithShifts() {
        Shift s = new Shift("18:00", "19:00", "02-02-2012", employeeOne);
        Shift s2 = new Shift("10:00", "19:00", "02-02-2012", employeeOne);
        Shift s3 = new Shift("18:00", "10:00", "02-02-2012", employeeOne);
        Shift s4 = new Shift("18:00", "19:00", "01-02-2012", employeeOne);
        Shift s5 = new Shift("18:00", "19:00", "02-02-2012", new Employee("a", "a", "a"));

        assertEquals(s.compareTo(s2), -1);
        assertEquals(s.compareTo(s3), -1);
        assertEquals(s.compareTo(s4), -1);
        assertEquals(s.compareTo(s5), -1);
        assertEquals(0, s.compareTo(s));
    }

    @After
    public void tearDown() throws SQLException {
        db.delete();
    }

}
