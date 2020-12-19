package shiftplanner;

import dao.Database;
import dao.EmployeeDao;
import dao.ShiftDao;
import dao.TaskDao;
import domain.Shift;
import domain.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.EmployeeService;
import services.ShiftService;
import services.TaskService;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public TimeSpinner fromTime;
    public TimeSpinner toTime;

    public Button addTaskButton;
    public Button editShiftButton;
    public Button deleteShiftButton;
    public Button deleteTaskButton;

    public TextField taskField;

    public ListView<Task> taskListView;

    public Button goBack;

    ObservableList<Task> taskList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shift = App.getShift();
        initDaos();
        initServices();
        initBackButton();
        initShiftInfo();
        initEditForm();
        initTaskListView();
        initAddTaskForm();
        initDeleteTaskButton();
        initDeleteShiftButton();
    }

    public void initDeleteShiftButton() {
        deleteShiftButton.setOnAction(actionEvent -> {

            taskService.deleteTasksByShift(shift);
            shiftService.deleteShift(shift);

            try {
                App.setRoot("ManageView");
            } catch (IOException e) {

            }
        });
    }

    public void initDeleteTaskButton() {
        deleteTaskButton.setOnAction(actionEvent -> {
            Task task = taskListView.getSelectionModel().getSelectedItem();
            taskService.deleteTask(task);
            setTasksToListView();
        });
    }

    public void initAddTaskForm() {
        addTaskButton.setOnAction(actionEvent -> {
            String taskName = taskField.getCharacters().toString();
            Task task = new Task(taskName, shift);
            taskService.addTask(task);
            setTasksToListView();
        });
    }

    public void initTaskListView() {
        setTasksToListView();
    }

    public void setTasksToListView() {
        taskList = FXCollections.observableArrayList();
        ArrayList<Task> l = taskService.getTasksByShift(shift);
        taskList.addAll(l);
        taskListView.setItems(taskList);
    }

    public void initEditForm() {
        editShiftButton.setOnAction(actionEvent -> {
            String from = fromTime.valueProperty().get().getHour() + ":" + fromTime.valueProperty().get().getMinute();
            String to = toTime.valueProperty().get().getHour() + ":" + toTime.valueProperty().get().getMinute();
            shiftService.editShift(shift, from, to);
            shift.setFrom(from);
            shift.setTo(to);
            fromText.setText(shift.getFrom());
            toText.setText(shift.getTo());
        });
    }

    public void initShiftInfo() {
        fromText.setText(shift.getFrom());
        toText.setText(shift.getTo());
        dateText.setText(shift.getDate());
    }

    public void initBackButton() {
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
