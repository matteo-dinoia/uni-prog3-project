package frontend.component;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.MailBox;

public class MailDetailController {
    // FXML
    @FXML private TextField fromText, toText, objText;
    @FXML private TextArea contentText;
    // Field
    private final MailBox mailBox = MailBox.mBoxTmp; // TODO change

    @FXML private void initialize() {
        fromText.textProperty().bind(mailBox.getSelectedMail().getFromProperty());
        toText.textProperty().bind(mailBox.getSelectedMail().getToProperty());
        objText.textProperty().bind(mailBox.getSelectedMail().getObjectProperty());
        contentText.textProperty().bind(mailBox.getSelectedMail().getContentProperty());
    }
}
