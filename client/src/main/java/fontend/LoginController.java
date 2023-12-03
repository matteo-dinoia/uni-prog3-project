package fontend;

import interfaces.LoginListener;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.MailAddress;


public class LoginController {
    // FXML
    @FXML private TextField usernameField;
    // Field
    private LoginListener listener = null;

    @FXML private void login(){
        if(listener == null){
            System.err.println("ERROR: could not callback main stage");
            return;
        }

        String mail = usernameField.getText();
        if(new MailAddress(mail).checkValidity())
            listener.continueAsUser(mail);
    }

    public void setContinueAsUser(LoginListener listener) {
        this.listener = listener;
    }
}
