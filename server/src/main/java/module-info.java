module server.server {
    requires javafx.controls;
    requires javafx.fxml;


    opens server.server to javafx.fxml;
    exports server.server;
}