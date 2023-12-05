module Server {
    requires javafx.controls;
    requires javafx.fxml;


    exports frontend;
    opens frontend to javafx.fxml;
}
