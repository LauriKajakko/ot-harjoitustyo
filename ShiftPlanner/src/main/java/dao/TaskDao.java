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
            boolean done = false;

            if (r.getInt("done") == 1) {
                done = true;
            }

            tasks.add(new Task(r.getString("name"), shift, done));
        }

        return tasks;
    }


}
