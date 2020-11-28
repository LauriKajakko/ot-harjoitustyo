package dao;

import java.io.File;
import java.sql.*;

public class Database {

    private Connection db;
    private String dbName;
    private Statement s;
    private PreparedStatement p;

    public Database(String dbName) throws SQLException {
        this.dbName = dbName;
        this.db = connect();
        createSchema();
    }

    public Connection connect() throws SQLException {
        try {
            Connection db = DriverManager.getConnection("jdbc:sqlite:" + this.dbName);
            return db;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public void createSchema() throws SQLException {
        s = db.createStatement();
        if (!tableExists("employees")) {
            s.execute("BEGIN TRANSACTION");
            s.execute("CREATE TABLE Employees (id INTEGER PRIMARY KEY, firstname TEXT, lastname TEXT, role TEXT)");
            s.execute("COMMIT;");
        }
        if (!tableExists("shifts")) {
            s.execute("BEGIN TRANSACTION");
            s.execute("CREATE TABLE Shifts (id INTEGER PRIMARY KEY, fromtime TEXT, totime TEXT, date TEXT,  employee_id INTEGER)");
            s.execute("COMMIT;");
        }
        if (!tableExists("tasks")) {
            s.execute("BEGIN TRANSACTION");
            s.execute("CREATE TABLE Tasks (id INTEGER PRIMARY KEY, name TEXT, done INTEGER, shift_id INTEGER)");
            s.execute("COMMIT;");
        }
    }

    public boolean tableExists(String tableName) throws SQLException {
        p = db.prepareStatement("SELECT name FROM sqlite_master WHERE type='table' AND name=(?)");
        p.setString(1, tableName);

        ResultSet res = p.executeQuery();

        boolean result = false;
        while (res.next()) {
            result = !res.getString("name").isBlank();
        }

        return result;
    }

    public void delete() {
        File databaseFile = new File(dbName);
        databaseFile.delete();
    }


}
