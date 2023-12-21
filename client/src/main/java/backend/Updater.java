package backend;

import frontend.App;
import javafx.application.Platform;
import model.MailBox;
import model.operationData.Operation;

import java.util.ArrayList;

public class Updater extends ServiceRequester<Boolean>{
    private final MailBox mailBox = App.singleMailBox;
    private int lastUpdate = Operation.OP_GETALL;

    @Override  public Boolean call() {
        boolean online = updateData();
        Platform.runLater(() -> mailBox.setOnline(online));
        return online;
    }

    private boolean updateData() {
        Operation requestUpdate = new Operation(mailBox.getOwner(), lastUpdate, Operation.NO_ERR, new ArrayList<>());
        Operation result = executeOperation(requestUpdate);

        if(result == null)
            return false;

        Platform.runLater(() -> mailBox.add(result.mailList()));
        lastUpdate = result.operation();
        return true;
    }
}
