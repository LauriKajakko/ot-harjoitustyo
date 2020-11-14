package otm2018;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class FXMLController implements Initializable {
    private Dice dice;

    public FXMLController() {
        dice = new Dice();
    }
     
    @FXML
    private Label display;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        dice.roll();
        display.setText("you got "+dice.getValue());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        display.setText("dice not rolled yet...");
    }    
}
