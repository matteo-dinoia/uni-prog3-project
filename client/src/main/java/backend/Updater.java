package backend;

import frontend.App;
import javafx.application.Platform;
import model.MailBox;
import model.operationData.Operation;

import java.util.ArrayList;

public class Updater extends ServiceRequester<Integer>{
    private final MailBox mailBox = App.singleMailBox;
    private int lastUpdate = Operation.OP_GETALL;

    /** Return number of new mail received (initial download doesn' count) */
    @Override public Integer call() {
        int lastUpdateOld = lastUpdate;
        Integer changedReceived = updateData();
        boolean online = changedReceived != null;

        if(lastUpdateOld == Operation.OP_GETALL)
            changedReceived = 0;

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
