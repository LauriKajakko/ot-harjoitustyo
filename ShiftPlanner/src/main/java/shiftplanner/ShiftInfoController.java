package shiftplanner;

import dao.Database;
import dao.EmployeeDao;
import dao.ShiftDao;
import dao.TaskDao;
import domain.Shift;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import services.EmployeeService;
import services.ShiftService;
import services.TaskService;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShiftInfoController implements Initializable {

    Shift shift;

    EmployeeDao employeeDao;
    ShiftDao shiftDao;
    TaskDao taskDao;

    EmployeeService employeeService;
    ShiftService shiftService;
    TaskService taskService;

    public Text fromText;
    public Text toText;
    public Text dateText;

    public Button goBack;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shift = App.getShift();
        initDaos();
        initServices();
        initButton();
        initShiftInfo();
    }

    public void initShiftInfo() {
        System.out.println("initshiftinfo: " + shift.getTo());
        fromText.setText(shift.getFrom());
        toText.setText(shift.getTo());
        dateText.setText(shift.getDate());
    }

    public void initButton() {
        goBack.setOnAction(actionEvent -> {
            try {
                App.setRoot("ManageView");
            } catch (IOException e) {

            }
        });
    }

    public void initDaos() {
        try {
            Database db = new Database("dev.db");
            Connection conn = db.connect();
            employeeDao = new EmployeeDao(conn);
            shiftDao = new ShiftDao(conn);
            taskDao = new TaskDao(conn);
        } catch (SQLException throwable) {

        }
    }

    public void initServices() {
        employeeService = new EmployeeService(employeeDao, shiftDao);
        shiftService = new ShiftService(shiftDao);
        taskService = new TaskService(taskDao);
    }

}
