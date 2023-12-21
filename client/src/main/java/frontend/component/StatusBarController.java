package frontend.component;

import frontend.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.MailBox;

public class StatusBarController {
    // FXML
    @FXML private Label statusLabel;
    // Field
    private final MailBox mailBox =  App.singleMailBox;

    @FXML private void initialize(){
        statusLabel.managedProperty().bind(mailBox.onlineProperty().not());
        statusLabel.visibleProperty().bind(mailBox.onlineProperty().not());
    }
}
