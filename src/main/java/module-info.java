module com.example.tasktracker1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.tasktracker1 to javafx.fxml;
    exports com.example.tasktracker1;
}