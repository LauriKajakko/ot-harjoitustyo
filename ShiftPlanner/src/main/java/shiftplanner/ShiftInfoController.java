package shiftplanner;

import dao.Database;
import dao.EmployeeDao;
import dao.ShiftDao;
import dao.TaskDao;
import domain.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.EmployeeService;
import services.ShiftService;
import services.TaskService;
import shiftplanner.App;
import utils.Conversions;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShiftInfoController implements Initializable {

    EmployeeDao employeeDao;
    ShiftDao shiftDao;
    TaskDao taskDao;

    EmployeeService employeeService;
    ShiftService shiftService;
    TaskService taskService;


    @FXML
    public Button goBack;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initDaos();
        initServices();
        initButton();
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
