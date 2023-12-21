package frontend.component;

import frontend.App;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.MailBox;

public class MailDetailController {
    // FXML
    @FXML private TextField fromText, toText, objText;
    @FXML private TextArea contentText;
    // Field
    private final MailBox mailBox = App.singleMailBox;

    @FXML private void initialize() {
        fromText.textProperty().bind(mailBox.getSelectedMail().fromProperty());
        toText.textProperty().bind(mailBox.getSelectedMail().toProperty());
        objText.textProperty().bind(mailBox.getSelectedMail().objectProperty());
        contentText.textProperty().bind(mailBox.getSelectedMail().contentProperty());
    }
}
