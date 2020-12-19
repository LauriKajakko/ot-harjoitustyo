package shiftplanner;

import dao.Database;
import dao.EmployeeDao;
import dao.ShiftDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import services.EmployeeService;


import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

public class DbTest {
    Database db;
    EmployeeService e;

    @Before
    public void setUp() throws SQLException {
        db = new Database("test.db");

    }

    @Test
    public void noDatabaseCreatesOne() throws SQLException {
        db.connect();

        assertTrue(db.tableExists("Shifts"));
        assertTrue(db.tableExists("Employees"));
        assertTrue(db.tableExists("Tasks"));
    }

    @After
    public void tearDown() {
        db.delete();
    }
}
