package frontend.component;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.MailBox;

public class StatusBarController {
    private final MailBox mailBox = MailBox.mBoxTmp;
    @FXML private Label statusLabel;

    @FXML private void initialize(){
        mailBox.getOnlineProperty().addListener((changed, oldV, newV) -> setOnline(newV));
        setOnline(mailBox.getOnlineProperty().get());
    }

    private void setOnline(boolean online) {
        if (online) statusLabel.setText("Online");
        else statusLabel.setText("Cannot connect server (possibly no internet)");
    }
}
