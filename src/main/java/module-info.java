module com.centennial.comp228.finaltest {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.centennial.comp228.finaltest to javafx.fxml;
    exports com.centennial.comp228.finaltest;
}