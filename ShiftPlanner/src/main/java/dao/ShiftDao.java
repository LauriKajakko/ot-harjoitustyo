package dao;

import domain.Shift;

import java.sql.*;
import java.util.ArrayList;

public class ShiftDao {

    private Connection db;
    private Statement s;
    private PreparedStatement p;

    /**
     * Constructor
     *
     * @throws SQLException
     */
    public ShiftDao() throws SQLException {
        Connection db = DriverManager.getConnection("jdbc:sqlite:dev.db");
        this.db = db;
    }

    /**
     * Constructor for testing
     *
     * @param db Test Database connection
     * @throws SQLException
     */
    public ShiftDao(Connection db) throws SQLException {
        this.db = db;
    }

    public void addShift(Shift shift) throws SQLException {
        p = db.prepareStatement("SELECT id FROM Employees WHERE firstname=(?) AND lastname=(?)");
        p.setString(1, shift.getEmployee().getFirstName());
        p.setString(2, shift.getEmployee().getLastName());
        ResultSet r = p.executeQuery();
        int employee_id = r.getInt("id");

        p = db.prepareStatement("INSERT INTO Shifts (fromtime, totime, date, employee_id) VALUES (?,?,?,?)");
        p.setString(1, shift.getFrom());
        p.setString(2, shift.getTo());
        p.setString(3, shift.getDate());
        p.setInt(4, employee_id);
        p.executeUpdate();
    }

    
}
