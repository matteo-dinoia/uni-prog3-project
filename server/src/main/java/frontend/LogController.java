package frontend;

import interfaces.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class LogController implements Logger {
    @FXML private TextArea logText;

    @FXML private void cleanOutput() {
        logText.setText("");
    }

    public void log(String str){
        if(str == null)
            str = "WARNING: logging null value";
        logText.appendText(str + "\n");
    }
}
