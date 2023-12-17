module Server {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    exports frontend;
    opens frontend to javafx.fxml;
    exports model.operationData;
    opens model.operationData;
}
