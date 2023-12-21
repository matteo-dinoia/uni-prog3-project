package backend;

import model.Mail;
import model.operationData.Operation;
import model.operationData.SimpleMail;

import java.util.Collections;
import java.util.List;

public class Deleter extends ServiceRequester<String>{
    private final Mail toDelete;
    private final String mailboxOwner;

    public Deleter(String owner, Mail mail) {
        this.toDelete = mail;
        this.mailboxOwner = owner;
    }

    /** Return error string -> if null then it succeded */
    @Override public String call() {
        List<SimpleMail> mailsToSend = Collections.singletonList(toDelete.getSimpleMail());
        Operation sendOperation = new Operation(mailboxOwner, Operation.OP_DELETE, Operation.NO_ERR, mailsToSend);
        Operation response = executeOperation(sendOperation);

        if (response == null)
            return "Error during communication with server";
        return null;
    }
}
