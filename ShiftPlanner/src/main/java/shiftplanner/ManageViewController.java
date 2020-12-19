package shiftplanner;


import dao.*;
import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import services.EmployeeService;
import services.ShiftService;
import services.TaskService;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageViewController implements Initializable {

    @FXML
    public ChoiceBox<Employee> choiceBox;
    public ListView<Shift> shiftListView;
    public TimeSpinner fromSpinner;
    public TimeSpinner toSpinner;

    public DatePicker datePicker;

    public Button addShiftButton;
    public Button addEmployeeButton;
    public Button employeeInfoButton;
    public Button shiftInfoButton;

    public TextField firstNameField;
    public TextField lastNameField;
    public TextField roleField;

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
        initInfoButtons();
        initAddShiftForm();
        initAddEmployeeForm();
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
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, employee, t1) -> {
            if (t1 != null){
                employeeValue = t1;
                setShiftsToListView(t1);
            } else {
                setShiftsToListView(employeeValue);
            }

        });
    }

    public void initAddShiftForm() {
        LocalDate t = LocalDate.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth());
        datePicker.valueProperty().setValue(t);
        employeeValue = choiceBox.getValue();
        fromValue = fromSpinner.valueProperty().get().getHour() + ":" + fromSpinner.valueProperty().get().getMinute();
        toValue = toSpinner.valueProperty().get().getHour() + ":" + toSpinner.valueProperty().get().getMinute();
        dateValue = datePicker.valueProperty().get().toString();

        fromSpinner.valueProperty().addListener((observableValue, localTime, t1) -> {
            fromValue = t1.toString();
        });
        toSpinner.valueProperty().addListener((observableValue, localTime, t1) -> {
            toValue = t1.toString();
        });
        datePicker.valueProperty().addListener((observableValue, localDate, t1) -> {
            dateValue = t1.toString();
        });

        addShiftButton.setOnAction(event -> {
            if (choiceBox.getValue() != null && toIsAfterFrom(fromValue, toValue)) {
                Shift shiftToAdd = new Shift(fromValue, toValue, dateValue, employeeValue);
                shiftService.addShift(shiftToAdd);
                setShiftsToListView(employeeValue);
            }
        });
    }

    public void initAddEmployeeForm() {
        addEmployeeButton.setOnAction(actionEvent -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String role = roleField.getText();
            firstNameField.clear();
            lastNameField.clear();
            roleField.clear();

            Employee employeeToAdd = new Employee(firstName, lastName, role);
            employeeService.addEmployee(employeeToAdd);
            updateChoiceBox();
        });
    }

    public void initInfoButtons() {
        employeeInfoButton.setOnAction(actionEvent -> {
            try {
                App.setEmployeeRoot(choiceBox.getValue());
            } catch (IOException e) {

            }
        });
        shiftInfoButton.setOnAction(actionEvent -> {
            try {
                if (!shiftListView.getSelectionModel().getSelectedItems().isEmpty()) {
                    App.setShiftRoot(shiftListView.getSelectionModel().getSelectedItem());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void updateChoiceBox() {
        employeeList = FXCollections.observableArrayList();
        ArrayList<Employee> list = employeeService.getAll();
        employeeList.addAll(list);
        choiceBox.setItems(employeeList);
    }

    public void setShiftsToListView(Employee employee) {
        shiftList = FXCollections.observableArrayList();
        ArrayList<Shift> l = shiftService.getShiftsByEmployee(employee);
        shiftList.addAll(l);
        shiftListView.setItems(shiftList);
    }

    private boolean toIsAfterFrom(String from, String to) {
        String[] splitFrom = from.split(":");
        String[] splitTo = to.split(":");
        if (Integer.parseInt(splitFrom[0]) >= Integer.parseInt(splitTo[0])) {
            if (Integer.parseInt(splitFrom[1]) >= Integer.parseInt(splitTo[1])) {
                return false;
            }
        }
        return true;
    }

}
