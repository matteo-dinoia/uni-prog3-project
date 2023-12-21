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

public class MailEditorController implements EndStatusNotifier<Mail> {
    // FXML
    @FXML private Label titleText, msgText;
    @FXML private TextField fromText, toText, objText;
    @FXML private TextArea contentText;
    @FXML private Button sendBtn;
    // Fields
    private Mail mailCreated;
    private EndStatusListener<Mail> listener;
    private boolean isSending = false;

    @FXML private void initialize(){ sendBtn.disableProperty().bind(App.singleMailBox.onlineProperty().not()); }

    private Mail getMail(){ return mailCreated; }
    public void setMail(Mail mail, boolean isDeletion){
        this.mailCreated = mail;

        fromText.textProperty().bind(mailCreated.fromProperty());
        toText.textProperty().bindBidirectional(mailCreated.toProperty());
        objText.textProperty().bindBidirectional(mailCreated.objectProperty());
        contentText.textProperty().bindBidirectional(mailCreated.contentProperty());

        if(isDeletion){
            titleText.setText("Deleting mail");
            sendBtn.setText("Delete");
            sendBtn.setTextFill(Color.RED);

            toText.setEditable(false);
            objText.setEditable(false);
            contentText.setEditable(false);
        }
    }

    public void setError(String errorStr){
        msgText.setText(errorStr);
        isSending = false;
    }

    @FXML private void cancel() { notifyListener(null); }
    @FXML private void send() { notifyListener(getMail()); }
    private void notifyListener(Mail res){
        if(listener == null || isSending) return;

        isSending = true;
        listener.useEndStatus(res);
    }

    @Override public void setOptionListener(EndStatusListener<Mail> listener) {
        this.listener = listener;
    }
}
