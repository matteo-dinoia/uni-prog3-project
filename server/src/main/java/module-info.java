module Server {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    exports frontend;
    exports model;
    opens model;
    opens frontend to javafx.fxml;
}
