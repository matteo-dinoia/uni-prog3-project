module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens frontend to javafx.fxml;
    exports frontend;
    exports interfaces;
    exports frontend.util;
    exports model;
    opens model;
    opens frontend.util to javafx.fxml;
    exports frontend.component;
    opens frontend.component to javafx.fxml;
    exports backend;
    opens backend;
    exports model.operationData;
    opens model.operationData;
}
