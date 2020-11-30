package shiftplanner;


import dao.*;
import domain.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeViewController implements Initializable {

    @FXML
    public ChoiceBox<Employee> choiceBox;
    public ListView<Shift> shiftListView;
    public Text infoText;
    public ToggleButton toggleButton;

    public EmployeeDao employeeDao;
    public ShiftDao shiftDao;
    public TaskDao taskDao;

    public EmployeeService employeeService;
    public ShiftService shiftService;
    public TaskService taskService;

    ObservableList<Employee> employeeList;
    ObservableList<Shift> shiftList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initDaos();
        initServices();
        initChoiceBox();
    }

    public void initDaos() {
        try {
            Database db = new Database("dev.db");
            Connection conn = db.connect();
            employeeDao = new EmployeeDao(conn);
            shiftDao = new ShiftDao(conn);
            taskDao = new TaskDao(conn);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void initServices() {
        this.employeeService = new EmployeeService(employeeDao, shiftDao);
        this.shiftService = new ShiftService(shiftDao);
        this.taskService = new TaskService(taskDao);
    }

    public void initChoiceBox() {
        employeeList = FXCollections.observableArrayList();
        employeeList.addAll(employeeService.getAll());
        choiceBox.setItems(employeeList);
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Employee>() {
            @Override
            public void changed(ObservableValue<? extends Employee> observableValue, Employee employee, Employee t1) {
                System.out.println("choiceBox value changed to: " + t1);
                setShiftsToListView(t1);
            }
        });
    }

    public void setShiftsToListView(Employee employee) {
        System.out.println("setShifts gets called");
        shiftList = FXCollections.observableArrayList();
        ArrayList<Shift> l = shiftService.getShiftsByEmployee(employee);
        shiftList.addAll(l);
        shiftListView.setItems(shiftList);
    }

    public void handleToggle(ActionEvent event) {
        System.out.println(event.getEventType());
        if (toggleButton.getText().equals("show shift info")) {
            toggleButton.setText("show employee info");

            infoText.setText(shiftListView.getSelectionModel().getSelectedItem().toString());
        } else {
            toggleButton.setText("show shift info");
            infoText.setText(choiceBox.getValue().toString());
        }
        System.out.println(shiftListView.getSelectionModel().getSelectedItem().toString());
    }
}
