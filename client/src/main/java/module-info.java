module Client {
    requires javafx.controls;
    requires javafx.fxml;


    opens fontend to javafx.fxml;
    exports fontend;
    exports interfaces;
    exports fontend.util;
    opens fontend.util to javafx.fxml;
}
