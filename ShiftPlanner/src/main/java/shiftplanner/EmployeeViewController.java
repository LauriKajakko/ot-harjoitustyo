package shiftplanner;

import dao.EmployeeDao;
import dao.ShiftDao;
import domain.Employee;
import domain.Shift;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeViewController implements Initializable {

    @FXML
    public ChoiceBox<String> choiceBox;
    public ListView<String> shiftListView;
    public Label shiftLabel;

    public EmployeeDao employeeDao;
    public ShiftDao shiftDao;
    ObservableList<String> employeeList;
    ObservableList<String> shiftList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeList = FXCollections.observableArrayList();

        try {
            employeeDao = new EmployeeDao();
            shiftDao = new ShiftDao();
            for (Employee employee: employeeDao.getAll())
                employeeList.add(employee.getFirstName() + " " + employee.getLastName());

        } catch (SQLException throwable) {
            System.out.println("database error \n there is probably no database or sum wrong wit it");
            throwable.printStackTrace();
        }

        initChoiceBox();

    }

    public void initChoiceBox() {
        choiceBox.setItems(employeeList);
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                System.out.println("choiceBox value changed to: " + t1);
                setShiftsToListView(t1);
            }
        });
    }

    public void setShiftsToListView(String name) {
        System.out.println("setShifts gets called");
        shiftList = FXCollections.observableArrayList();
        String[] names = name.split(" ");
        try {
            ArrayList<Shift> l = shiftDao.getShiftsByEmployee(employeeDao.getByName(names[0], names[1]));
            for (Shift shift: l) {
                shiftList.add(shift.getDate());
            }
            shiftListView.setItems(shiftList);
        } catch (SQLException throwable) {
            System.out.println("database error");
            throwable.printStackTrace();
        }

    }


}
