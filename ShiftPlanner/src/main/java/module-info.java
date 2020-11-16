module shiftplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens shiftplanner to javafx.fxml;
    exports shiftplanner;
}