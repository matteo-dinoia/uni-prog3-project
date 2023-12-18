package frontend;

import interfaces.EndStatusListener;
import interfaces.EndStatusNotifier;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.Mail;
import model.MailBox;

public class MailEditorController implements EndStatusNotifier<Mail> {
    // FXML
    @FXML private Label titleText;
    @FXML private TextField fromText, toText, objText;
    @FXML private TextArea contentText;
    @FXML private Button sendBtn;
    // Fields
    private Mail mailCreated;
    private EndStatusListener<Mail> listener;

    public void setMail(Mail mail, boolean isDeletion){
        this.mailCreated = mail;

        fromText.textProperty().bind(mailCreated.getFromProperty());
        toText.textProperty().bindBidirectional(mailCreated.getToProperty());
        objText.textProperty().bindBidirectional(mailCreated.getObjectProperty());
        contentText.textProperty().bindBidirectional(mailCreated.getContentProperty());

        if(isDeletion){
            titleText.setText("Deleting mail");
            sendBtn.setText("Delete");
            sendBtn.setTextFill(Color.RED);

            toText.setEditable(false);
            objText.setEditable(false);
            contentText.setEditable(false);
        }
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

    public void initializeModel(MailBox mailBox) {
        sendBtn.disableProperty().bind(mailBox.onlineProperty().not());
    }
}
