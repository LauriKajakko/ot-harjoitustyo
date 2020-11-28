package shiftplanner;

import dao.Database;
import dao.EmployeeDao;
import dao.ShiftDao;
import domain.Employee;
import domain.Shift;
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
    ObservableList<Employee> employeeList;
    ObservableList<Shift> shiftList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        employeeList = FXCollections.observableArrayList();
        try {
            Database db = new Database("dev.db");
            Connection conn = db.connect();
            employeeDao = new EmployeeDao(conn);
            shiftDao = new ShiftDao(conn);
            for (Employee employee: employeeDao.getAll()) {
                employeeList.add(employee);
            }
        } catch (SQLException throwable) {
            System.out.println("database error \n there is probably no database or sum wrong wit it");
            throwable.printStackTrace();
        }

        initChoiceBox();

    }

    public void initChoiceBox() {
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
        try {
            ArrayList<Shift> l = shiftDao.getShiftsByEmployee(employee);
            for (Shift shift: l) {
                shiftList.add(shift);
            }
            shiftListView.setItems(shiftList);
        } catch (SQLException throwable) {
            System.out.println("database error");
            throwable.printStackTrace();
        }

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
