module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens fontend to javafx.fxml;
    exports fontend;
    exports interfaces;
    exports fontend.util;
    exports model;
    opens model;
    opens fontend.util to javafx.fxml;
}
