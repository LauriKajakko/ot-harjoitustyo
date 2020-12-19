package shiftplanner;

import dao.Database;
import dao.EmployeeDao;
import dao.ShiftDao;
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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class ServiceTest {

    Database db;
    EmployeeDao employeeDao;
    ShiftDao shiftDao;

    EmployeeService employeeService;
    ShiftService shiftService;

    @Before
    public void setUp() throws SQLException {
        db = new Database("test2.db");
        Connection connection = db.connect();

        employeeDao = new EmployeeDao(connection);
        shiftDao = new ShiftDao(connection);

        this.employeeService = new EmployeeService(employeeDao, shiftDao);
        this.shiftService = new ShiftService(shiftDao);

        this.employeeService.addEmployee(new Employee("lauri", "kajakko", "ceo"));
        this.shiftService.addShift(new Shift("8:00" , "16:00", "02-02-2020", new Employee("lauri", "kajakko", "ceo")));
    }

    @Test
    public void gettingEmployeesWorks() {
        ArrayList<Employee> l = employeeService.getAll();
        assertEquals(l.get(0).toString(), new Employee("lauri", "kajakko", "ceo").toString());
    }

    @Test
    public void gettingShiftsByEmployeeWorks() {
        ArrayList<Shift> l = shiftService.getShiftsByEmployee(new Employee("lauri", "kajakko", "ceo"));
        assertEquals(l.get(0).toString(), new Shift("8:00" , "16:00", "02-02-2020", new Employee("lauri", "kajakko", "ceo")).toString());
    }

    @Test
    public void addingEmployeesWorks() {
        employeeService.addEmployee(new Employee("essi", "esimerkki", "cto"));
        ArrayList<Employee> l = employeeService.getAll();
        assertEquals(l.get(1).toString(), new Employee("essi", "esimerkki", "cto").toString());
    }

    @Test
    public void addingShiftsWorks() {
        shiftService.addShift(new Shift("8:00" , "16:00", "02-03-2020", new Employee("lauri", "kajakko", "ceo")));
        ArrayList<Shift> l = shiftService.getShiftsByEmployee(new Employee("lauri", "kajakko", "ceo"));
        assertEquals(l.get(1).toString(), new Shift("8:00" , "16:00", "02-03-2020", new Employee("lauri", "kajakko", "ceo")).toString());
    }

    @After
    public void tearDown() throws SQLException {
        db.delete();
    }

}
