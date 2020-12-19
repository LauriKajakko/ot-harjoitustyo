package shiftplanner;

import domain.Employee;
import domain.Shift;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Employee employeeForInfo;
    private static Shift shiftForInfo;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("ManageView"), 1000, 480);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

        stage.setMinWidth(1000);
        stage.setMinHeight(480);
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    static void setEmployeeRoot(Employee employee) throws IOException {
        employeeForInfo = employee;
        setRoot("EmployeeInfo");
    }

    static Employee getEmployee() {
        return employeeForInfo;
    }

    static void setShiftRoot(Shift shift) throws IOException {
        shiftForInfo = shift;
        setRoot("ShiftInfo");
    }

    static Shift getShift() {
        return shiftForInfo;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}