package dao;

import domain.Employee;
import domain.Shift;
import domain.Task;

import java.sql.*;
import java.util.ArrayList;

public class ShiftDao {

    private Connection db;
    private Statement s;
    private PreparedStatement p;

    /**
     *
     * @param db Test Database connection
     * @throws SQLException
     */
    public ShiftDao(Connection db) throws SQLException {
        this.db = db;
    }

    /**
     *
     * @param employee whose shifts are retrieved form database
     * @return employee's shifts as an ArrayList
     * @throws SQLException
     */
    public ArrayList<Shift> getShiftsByEmployee(Employee employee) throws SQLException {
        ArrayList<Shift> shifts = new ArrayList<>();
        p = db.prepareStatement("SELECT fromtime, totime, date FROM Shifts WHERE employee_id=(SELECT id FROM Employees WHERE firstname=(?) AND lastname=(?))");
        p.setString(1, employee.getFirstName());
        p.setString(2, employee.getLastName());
        ResultSet r = p.executeQuery();

        while (r.next()) {
            shifts.add(new Shift(r.getString("fromtime"), r.getString("totime"), r.getString("date"), employee));
        }
        return shifts;

    }


    /**
     *
     * @param shift shift to add to database
     * @throws SQLException
     */
    public void addShift(Shift shift) throws SQLException {
        p = db.prepareStatement("SELECT id FROM Employees WHERE firstname=(?) AND lastname=(?)");
        p.setString(1, shift.getEmployee().getFirstName());
        p.setString(2, shift.getEmployee().getLastName());
        ResultSet r = p.executeQuery();
        int employeeId = r.getInt("id");

        p = db.prepareStatement("INSERT INTO Shifts (fromtime, totime, date, employee_id) VALUES (?,?,?,?)");
        p.setString(1, shift.getFrom());
        p.setString(2, shift.getTo());
        p.setString(3, shift.getDate());
        p.setInt(4, employeeId);
        p.executeUpdate();
    }

    /**
     * Edits the time of the shift in the DB
     *
     * @param shift to edit
     * @param newFrom new beginning time
     * @param newTo new end time
     * @throws SQLException
     */
    public void editShift(Shift shift, String newFrom, String newTo) throws SQLException {
        p = db.prepareStatement("UPDATE Shifts SET fromtime=(?), totime=(?) WHERE fromtime=(?) AND totime=(?) AND date=(?) AND employee_id=(SELECT id FROM Employees WHERE firstname=(?) AND lastname=(?) )");
        p.setString(1, newFrom);
        p.setString(2, newTo);
        p.setString(3, shift.getFrom());
        p.setString(4, shift.getTo());
        p.setString(5, shift.getDate());
        p.setString(6, shift.getEmployee().getFirstName());
        p.setString(7, shift.getEmployee().getLastName());
        p.executeUpdate();
    }

    /**
     * Deletes the shift from DB
     *
     * @param shift to delete
     * @throws SQLException
     */
    public void deleteShift(Shift shift) throws SQLException {
        p = db.prepareStatement("DELETE FROM Shifts WHERE fromtime=(?) AND totime=(?) AND date=(?) AND employee_id=(SELECT id FROM Employees WHERE firstname=(?) AND lastname=(?) )");
        p.setString(1, shift.getFrom());
        p.setString(2, shift.getTo());
        p.setString(3, shift.getDate());
        p.setString(4, shift.getEmployee().getFirstName());
        p.setString(5, shift.getEmployee().getLastName());
        p.executeUpdate();
    }
}
