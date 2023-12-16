package fontend;

import interfaces.EndStatusListener;
import interfaces.EndStatusNotifier;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Mail;

public class MailEditorController implements EndStatusNotifier<Mail> {
    // FXML
    @FXML private TextField toText;
    @FXML private TextField objText;
    @FXML private TextArea contentText;
    // Fields
    private Mail mailCreated;
    private EndStatusListener<Mail> listener;

    public void setDefaultMail(Mail mail){
        this.mailCreated = mail;

        toText.textProperty().bindBidirectional(mailCreated.getToProperty());
        objText.textProperty().bindBidirectional(mailCreated.getObjectProperty());
        contentText.textProperty().bindBidirectional(mailCreated.getContentProperty());
    }

    private Mail getMail(){
        return mailCreated;
    }

    @FXML private void cancel() { notifyListener(null); }
    @FXML private void send() { notifyListener(getMail()); }
    private void notifyListener(Mail res){ if(listener != null) listener.useEndStatus(res); }

    @Override public void setOptionListener(EndStatusListener<Mail> listener) {
        this.listener = listener;
    }
}
