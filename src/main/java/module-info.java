module com.example.tasktracker1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;


    opens com.example.tasktracker1 to javafx.fxml;
    exports com.example.tasktracker1;
    exports com.example.tasktracker1.database;
    opens com.example.tasktracker1.database to javafx.fxml;
    exports com.example.tasktracker1.Controllers;
    opens com.example.tasktracker1.Controllers to javafx.fxml;
    exports com.example.tasktracker1.operators;
    opens com.example.tasktracker1.operators to javafx.fxml;
    exports com.example.tasktracker1.anim;
    opens com.example.tasktracker1.anim to javafx.fxml;
    exports com.example.tasktracker1.util;
    opens com.example.tasktracker1.util to javafx.fxml;
}