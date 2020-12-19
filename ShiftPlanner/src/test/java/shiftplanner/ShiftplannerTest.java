package shiftplanner;

import dao.Database;
import dao.EmployeeDao;
import dao.ShiftDao;
import dao.TaskDao;
import org.junit.Before;
import domain.Employee;
import domain.Shift;
import domain.Task;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import services.EmployeeService;
import services.ShiftService;
import services.TaskService;
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
        assertTrue(expected.get(expected.size()-1).compareTo(result.get(result.size()-1)) == 0);
    }


    @Test
    public void editingShiftWorks() {
        ArrayList<Shift> first = shiftService.getShiftsByEmployee(employeeOne);
        Shift s = first.get(0);
        shiftService.editShift(s, "00:00", "12:34");
        Shift result = shiftService.getShiftsByEmployee(employeeOne).get(0);

        assertTrue(result.getFrom().equals("00:00"));
        assertTrue(result.getTo().equals("12:34"));
    }

    @Test
    public void addingTaskWorks() {
        Task exp = new Task("clean up", new Shift("8:00" , "16:00", "02-02-2020", employeeOne));
        taskService.addTask(exp);
        Task res = taskService.getTasksByShift(new Shift("8:00" , "16:00", "02-02-2020", employeeOne)).get(0);
        assertTrue(exp.compareTo(res) == 0);
    }

    @Test
    public void deletingTaskWorks() {
        Task exp = new Task("clean up", new Shift("8:00" , "16:00", "02-02-2020", employeeOne));
        taskService.addTask(exp);
        assertTrue(taskService.getTasksByShift(new Shift("8:00" , "16:00", "02-02-2020", employeeOne)).size()==1);
        taskService.deleteTask(exp);
        assertTrue(taskService.getTasksByShift(new Shift("8:00" , "16:00", "02-02-2020", employeeOne)).size() == 0);
    }

    @Test
    public void comparingEmployeesReturnsZeroOrMinusOne() {
        Employee e = new Employee("lauri", "kajakko", "ceo");
        assertTrue(e.compareTo(employeeOne) == 0);
        Employee e2 = new Employee("lauri", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "ceo");
        assertTrue(e2.compareTo(employeeOne) == -1);
    }


    @After
    public void tearDown() throws SQLException {
        db.delete();
    }

}
