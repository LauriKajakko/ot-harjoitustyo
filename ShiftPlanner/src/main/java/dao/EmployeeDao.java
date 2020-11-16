package dao;

import domain.Employee;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDao {

    private Connection db;
    private Statement s;
    private PreparedStatement p;

    public EmployeeDao(Connection db) throws SQLException {
        this.db=db;
    }

    public ArrayList<Employee>  getAll() throws SQLException{
        p = db.prepareStatement("SELECT * FROM Employees");
        ResultSet r = p.executeQuery();
        return new ArrayList<>();

    }

}
