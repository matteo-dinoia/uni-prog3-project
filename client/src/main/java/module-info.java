module Client {
    requires javafx.controls;
    requires javafx.fxml;


    opens fontend to javafx.fxml;
    exports fontend;
    exports interfaces;
}
