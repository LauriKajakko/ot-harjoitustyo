module shiftplanner {
    requires javafx.controls;
    requires javafx.fxml;

    opens shiftplanner to javafx.fxml;
    exports shiftplanner;
}