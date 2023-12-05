package fontend;

import interfaces.EndStatusListener;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.MailAddress;


public class LoginController {
    // FXML
    @FXML private TextField usernameField;
    // Field
    private EndStatusListener<String> listener = null;

    @FXML private void login(){
        if(listener == null){
            System.err.println("ERROR: could not callback main stage");
            return;
        }

        String mail = usernameField.getText();
        if(new MailAddress(mail).checkValidity())
            listener.useEndStatus(mail);
    }

    public void setContinueAsUser(EndStatusListener<String> listener) {
        this.listener = listener;
    }
}
