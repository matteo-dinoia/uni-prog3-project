package backend;

import javafx.application.Platform;
import model.MailBox;
import model.operationData.Operation;

import java.util.ArrayList;

// TODO need to be scheduled
public class Updater extends ServiceRequester<Boolean>{
    private final MailBox mailBox;
    private int lastUpdate = Operation.OPERATION_GETALL;


    public Updater(MailBox mailBox){
        this.mailBox = mailBox;
    }

    @Override  public Boolean call() {
        boolean online = updateData();
        Platform.runLater(() -> mailBox.setOnline(online));
        return online;
    }

    private boolean updateData() {
        Operation requestUpdate = new Operation(mailBox.getOwner(), lastUpdate, new ArrayList<>());
        Operation result = executeOperation(requestUpdate);

        if(result == null)
            return false;

        Platform.runLater(() -> mailBox.add(result.mailList()));
        lastUpdate = 1;
        return true;
    }
}
