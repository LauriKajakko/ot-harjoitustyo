package dao;

import domain.Shift;
import domain.Task;

import java.sql.*;
import java.util.ArrayList;

public class TaskDao {

    private Connection db;
    private Statement s;
    private PreparedStatement p;


    public TaskDao(Connection db) {
        this.db = db;
    }

    public ArrayList<Task> getTasksByShift(Shift shift) throws SQLException {
        p = db.prepareStatement(
                "SELECT * FROM Tasks WHERE shift_id=(SELECT id FROM Shifts WHERE fromtime=(?) AND totime=(?) AND date=(?) AND employee_id=(SELECT id FROM Employees WHERE firstname=(?) AND lastname=(?)))"
        );

        p.setString(1, shift.getFrom());
        p.setString(2, shift.getTo());
        p.setString(3, shift.getDate());
        p.setString(4, shift.getEmployee().getFirstName());
        p.setString(5, shift.getEmployee().getLastName());

        ResultSet r = p.executeQuery();

        ArrayList<Task> tasks = new ArrayList<>();

        while (r.next()) {
            tasks.add(new Task(r.getString("name"), shift));
        }

        return tasks;
    }

    public void deleteTasksByShift(Shift shift) throws SQLException {
        p = db.prepareStatement("DELETE FROM Tasks WHERE shift_id=(SELECT id FROM Shifts WHERE fromtime=(?) AND totime=(?) AND date=(?) AND employee_id=(SELECT id FROM Employees WHERE firstname=(?) AND lastname=(?) ))");
        p.setString(1, shift.getFrom());
        p.setString(2, shift.getTo());
        p.setString(3, shift.getDate());
        p.setString(4, shift.getEmployee().getFirstName());
        p.setString(5, shift.getEmployee().getLastName());
        p.executeUpdate();
    }

    public void addTask(Task task) throws SQLException {
        p = db.prepareStatement("SELECT id FROM Shifts WHERE fromtime=(?) AND totime=(?) AND date=(?) AND employee_id=(SELECT id FROM Employees WHERE firstname=(?) AND lastname=(?) )");
        p.setString(1, task.getShift().getFrom());
        p.setString(2, task.getShift().getTo());
        p.setString(3, task.getShift().getDate());
        p.setString(4, task.getShift().getEmployee().getFirstName());
        p.setString(5, task.getShift().getEmployee().getLastName());
        ResultSet r = p.executeQuery();
        int id = r.getInt(1);

        p = db.prepareStatement("INSERT INTO Tasks (name, shift_id) VALUES (?,?)");
        p.setString(1, task.getName());
        p.setInt(2, id);
        p.executeUpdate();
    }

    public void deleteTask(Task task) throws SQLException {
        p = db.prepareStatement("DELETE FROM Tasks WHERE name=(?) AND shift_id=(SELECT id FROM Shifts WHERE fromtime=(?) AND totime=(?) AND date=(?) AND employee_id=(SELECT id FROM Employees WHERE firstname=(?) AND lastname=(?) ))");
        p.setString(1, task.getName());
        p.setString(2, task.getShift().getFrom());
        p.setString(3, task.getShift().getTo());
        p.setString(4, task.getShift().getDate());
        p.setString(5, task.getShift().getEmployee().getFirstName());
        p.setString(6, task.getShift().getEmployee().getLastName());
        p.executeUpdate();
    }
}
