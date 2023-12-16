package fontend.component;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.MailBox;

public class StatusBarController {
    private final MailBox mailBox = MailBox.mBoxTmp;
    @FXML private Label statusLabel;

    @FXML private void initialize(){
        mailBox.getOnlineProperty().addListener((changed, oldV, newV) -> setOnline(newV));
    }

    private void setOnline(boolean online) {
        if (online) statusLabel.setText("Online");
        else statusLabel.setText("Cannot connect server (possibly no internet)");
    }
}
