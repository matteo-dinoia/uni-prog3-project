module client.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens client.client to javafx.fxml;
    exports client.client;
}