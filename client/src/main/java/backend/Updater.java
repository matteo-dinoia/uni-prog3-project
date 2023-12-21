package backend;

import frontend.App;
import javafx.application.Platform;
import model.MailBox;
import model.operationData.Operation;

import java.util.ArrayList;

public class Updater extends ServiceRequester<Integer>{
    private final MailBox mailBox = App.singleMailBox;
    private int lastUpdate = Operation.OP_GETALL;

    @Override  public Integer call() {
        Integer changed = updateData();
        boolean online = changed != null;

        Platform.runLater(() -> mailBox.setOnline(online));
        return changed;
    }

    private Integer updateData() {
        Operation requestUpdate = new Operation(mailBox.getOwner(), lastUpdate, Operation.NO_ERR, new ArrayList<>());
        Operation result = executeOperation(requestUpdate);

        if(result == null)
            return null;

        Platform.runLater(() -> mailBox.add(result.mailList()));
        lastUpdate = result.operation();
        return result.mailList().size();
    }
}
