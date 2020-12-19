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
import utils.Conversions;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeInfoController implements Initializable {

    EmployeeDao employeeDao;
    ShiftDao shiftDao;
    EmployeeService employeeService;
    Conversions conversions;


    public BarChart<String, Number> hourChart;
    public Text employeeName;
    public Text employeeLastname;
    public Text employeeRole;
    public Button goBack;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conversions = new Conversions();
        initDaos();
        initServices();
        initHourChart();
        initName();
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
        } catch (SQLException throwable) {
        }
    }

    public void initServices() {
        employeeService = new EmployeeService(employeeDao, shiftDao);
    }

    public void initHourChart() {
        String[][] l = employeeService.getLastWorkDays(App.getEmployee(), 7);
        XYChart.Series ds = new XYChart.Series();
        for (String[] s : l){
            ds.getData().add(new XYChart.Data(s[1], conversions.minutesToHours(s[0])));
        }
        hourChart.getData().add(ds);
    }

    public void initName() {
        Employee e = App.getEmployee();
        employeeName.setText(e.getFirstName());
        employeeLastname.setText(e.getLastName());
        employeeRole.setText(e.getRole());
    }


}
