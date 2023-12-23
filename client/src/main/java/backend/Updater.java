package backend;

import frontend.App;
import model.MailBox;
import model.operationData.Operation;
import model.operationData.SimpleMail;
import java.util.ArrayList;
import java.util.List;

public class Updater extends ServiceRequester<List<SimpleMail>>{
    private final MailBox mailBox = App.singleMailBox;
    private int lastUpdate = Operation.OP_GETALL;

    /** Return the new mail got from server */
    @Override public List<SimpleMail> call() {
        Operation requestUpdate = new Operation(mailBox.getOwner(), lastUpdate, Operation.NO_ERR, new ArrayList<>());
        Operation result = executeOperation(requestUpdate);

        if(result == null) return null;

        lastUpdate = result.operation();
        return result.mailList();
    }
}
