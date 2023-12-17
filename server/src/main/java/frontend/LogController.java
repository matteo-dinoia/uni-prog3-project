package frontend;

import interfaces.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class LogController implements Logger {
    // FXML
    @FXML private ListView<String> logList;
    // Field
    private final ObservableList<String> logs = FXCollections.observableArrayList();

    @FXML private void initialize(){
        logList.setItems(logs);
    }

    @FXML private void cleanOutput() { logs.clear(); }

    @Override public synchronized void log(String str){
        if(str == null)
            str = "WARNING: logging null value";
        logs.add(str);
    }
}
