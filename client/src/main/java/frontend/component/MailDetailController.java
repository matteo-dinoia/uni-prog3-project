package frontend.component;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import model.MailBox;

public class MailDetailController {
    // FXML
    @FXML private TextArea contentText;
    private final MailBox mailBox = MailBox.mBoxTmp;

    @FXML private void initialize() {
        mailBox.selectedMail.getFromProperty().addListener((vhanged, oldV, newV) -> updateMail());
        mailBox.selectedMail.getToProperty().addListener((vhanged, oldV, newV) -> updateMail());
        mailBox.selectedMail.getObjectProperty().addListener((vhanged, oldV, newV) -> updateMail());
        mailBox.selectedMail.getContentProperty().addListener((vhanged, oldV, newV) -> updateMail());
        updateMail();
    }

    private void updateMail(){
        contentText.setText( mailBox.selectedMail.formatted());
    }
}
