package frontend.component;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.MailBox;

public class StatusBarController {
    // FXML
    @FXML private Label statusLabel;
    // Field
    private final MailBox mailBox = MailBox.mBoxTmp; // TODO change

    @FXML private void initialize(){
        mailBox.onlineProperty().addListener((changed, oldV, newV) -> setOnline(newV));
        setOnline(mailBox.onlineProperty().get());
    }

    private void setOnline(boolean online) {
        if (online) statusLabel.setText("Online");
        else statusLabel.setText("Cannot connect server (possibly no internet)");
    }
}
