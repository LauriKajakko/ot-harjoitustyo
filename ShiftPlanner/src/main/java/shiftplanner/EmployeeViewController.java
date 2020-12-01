package shiftplanner;


import dao.*;
import domain.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeViewController implements Initializable {

    @FXML
    public ChoiceBox<Employee> choiceBox;
    public ListView<Shift> shiftListView;
    public Text infoText;
    public ToggleButton toggleButton;
    public TimeSpinner fromSpinner;
    public TimeSpinner toSpinner;
    public DatePicker datePicker;
    public Button addButton;

    public EmployeeDao employeeDao;
    public ShiftDao shiftDao;
    public TaskDao taskDao;

    public EmployeeService employeeService;
    public ShiftService shiftService;
    public TaskService taskService;

    private String fromValue;
    private String toValue;
    private String dateValue;
    private Employee employeeValue;

    ObservableList<Employee> employeeList;
    ObservableList<Shift> shiftList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initDaos();
        initServices();
        initChoiceBox();
        initAddShiftForm();
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
        this.employeeService = new EmployeeService(employeeDao, shiftDao);
        this.shiftService = new ShiftService(shiftDao);
        this.taskService = new TaskService(taskDao);
    }

    public void initChoiceBox() {
        employeeList = FXCollections.observableArrayList();
        ArrayList<Employee> list = employeeService.getAll();
        employeeList.addAll(list);
        choiceBox.setItems(employeeList);
        choiceBox.setValue(list.get(0));
        setShiftsToListView(list.get(0));
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, employee, t1) -> {
            employeeValue = t1;
            setShiftsToListView(t1);
        });
    }

    public void initAddShiftForm() {
        employeeValue = choiceBox.getValue();
        fromValue = fromSpinner.valueProperty().get().getHour() + ":" + fromSpinner.valueProperty().get().getMinute();
        toValue = toSpinner.valueProperty().get().getHour() + ":" + toSpinner.valueProperty().get().getMinute();

        fromSpinner.valueProperty().addListener((observableValue, localTime, t1) -> {
            fromValue = t1.toString();
        });
        toSpinner.valueProperty().addListener((observableValue, localTime, t1) -> {
            toValue = t1.toString();
        });
        datePicker.valueProperty().addListener((observableValue, localDate, t1) -> {
            dateValue = t1.toString();
        });

        addButton.setOnAction(event -> {
            Shift shiftToAdd = new Shift(fromValue, toValue, dateValue, employeeValue);
            shiftService.addShift(shiftToAdd);
            setShiftsToListView(employeeValue);
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
            if (shiftListView.getSelectionModel().isEmpty()) {
                infoText.setText("select a shift from the list");
            } else {
                String info = shiftListView.getSelectionModel().getSelectedItem().toString();
                infoText.setText(info);
            }

        } else {
            toggleButton.setText("show shift info");
            infoText.setText(
                    "First name: " + employeeValue.getFirstName() + "\n"  +
                    "Last name: " + employeeValue.getLastName() + "\n" +
                    "Role: " + employeeValue.getRole());
        }
    }
}
