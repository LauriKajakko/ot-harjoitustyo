package shiftplanner;

import dao.EmployeeDao;
import dao.ShiftDao;
import domain.Employee;
import domain.Shift;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeViewController implements Initializable {

    @FXML
    public ChoiceBox<String> choiceBox;
    public ListView<Shift> shiftList;

    public EmployeeDao employeeDao;
    public ShiftDao shiftDao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList();

        try {
            employeeDao = new EmployeeDao();
            shiftDao = new ShiftDao();
            for (Employee employee: employeeDao.getAll())
                list.add(employee.getFirstName() + " " + employee.getLastName());

        } catch (SQLException throwable) {
            System.out.println("database error \n there is probably no database or sum wrong wit it");
            throwable.printStackTrace();
        }

        choiceBox.setItems(list);
    }

    /*
    TODO public void handleChoiceBoxChange(ActionEvent event){
        choiceBox.setOnAction((event1 -> {
            shiftList.setItems(shiftDao.getShiftsByEmployee());
        }));
    }
*/


}
