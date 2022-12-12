module com.example.sqlassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.sqlassignment to javafx.fxml;
    exports com.example.sqlassignment;
}