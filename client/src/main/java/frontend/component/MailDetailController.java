package frontend.component;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import model.MailBox;

public class MailDetailController {
    // FXML
    @FXML private TextArea contentText;
    // Field
    private final MailBox mailBox = MailBox.mBoxTmp; // TODO change

    @FXML private void initialize() {
        mailBox.getSelectedMail().getFromProperty().addListener((vhanged, oldV, newV) -> updateMail());
        mailBox.getSelectedMail().getToProperty().addListener((vhanged, oldV, newV) -> updateMail());
        mailBox.getSelectedMail().getObjectProperty().addListener((vhanged, oldV, newV) -> updateMail());
        mailBox.getSelectedMail().getContentProperty().addListener((vhanged, oldV, newV) -> updateMail());
        updateMail();
    }

    private void updateMail(){
        contentText.setText(mailBox.getSelectedMail().formatted());
    }
}
