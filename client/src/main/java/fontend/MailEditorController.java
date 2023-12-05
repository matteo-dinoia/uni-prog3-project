package fontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MailEditorController {

    @FXML private void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML private void send(ActionEvent actionEvent) {
        // TODO fix this
        cancel(actionEvent);
    }
}
