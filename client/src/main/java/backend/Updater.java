package backend;

import frontend.App;
import javafx.application.Platform;
import model.MailBox;
import model.operationData.Operation;

import java.util.ArrayList;

public class Updater extends ServiceRequester<Integer>{
    private final MailBox mailBox = App.singleMailBox;
    private int lastUpdate = Operation.OP_GETALL;
    private boolean firstTime = true;

    /** Return number of new mail received (initial download doesn' count) */
    @Override public Integer call() {
        Integer changedReceived = updateData();
        boolean online = changedReceived != null;

        if(online && firstTime){
            firstTime = false;
            changedReceived = 0;
        }

        Platform.runLater(() -> mailBox.setOnline(online));
        return changedReceived;
    }

    private Integer updateData() {
        Operation requestUpdate = new Operation(mailBox.getOwner(), lastUpdate, Operation.NO_ERR, new ArrayList<>());
        Operation result = executeOperation(requestUpdate);

        if(result == null)
            return null;

        Platform.runLater(() -> mailBox.add(result.mailList()));
        lastUpdate = result.operation();
        return result.mailList().stream().filter((mail) -> !mail.source().equals(mailBox.getOwner())).toList().size();
    }
}
