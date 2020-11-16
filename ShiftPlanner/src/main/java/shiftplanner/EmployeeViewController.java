package shiftplanner;

import dao.EmployeeDao;
import domain.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeViewController implements Initializable {

    @FXML
    public ChoiceBox<String> choiceBox;
    public TextField firstName;
    public TextField lastName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList();

        try {
            EmployeeDao employeeDao = new EmployeeDao();
            for (Employee employee: employeeDao.getAll())
                list.add(employee.getFirstName() + " " + employee.getLastName());

        } catch (SQLException throwable) {
            System.out.println("database error \n there is probably no database or sum wrong wit it");
            throwable.printStackTrace();
        }

        choiceBox.setItems(list);
    }

}
