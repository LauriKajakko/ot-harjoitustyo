package shiftplanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeViewController implements Initializable {

    @FXML
    public ChoiceBox<String> choiceBox;
    public TextField firstName;
    public TextField lastName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("employee1", "employee2", "employee3");
        if(choiceBox!=null) choiceBox.setItems(list);
    }

}
