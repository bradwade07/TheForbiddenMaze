module Group2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens Group2 to javafx.fxml;
    exports Group2;
    exports UI;
    opens UI to javafx.fxml;
}