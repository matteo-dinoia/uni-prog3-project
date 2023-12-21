package backend;

import model.Mail;
import model.MailValidator;
import model.operationData.Operation;
import model.operationData.SimpleMail;
import java.util.Collections;
import java.util.List;

public class Sender extends ServiceRequester<String> {
    private final String mailboxOwner;
    private final Mail toSend;
    private final MailValidator validator = new MailValidator();

    public Sender(String owner, Mail toSend){
        this.toSend = toSend;
        this.mailboxOwner = owner;
    }

    // Return error string -> if null then it succeded
    @Override public String call() {
        if(toSend == null || !validator.checkValidity(toSend))
            return "Invalid mail (invalid addresses or empty object)";

        List<SimpleMail> mailsToSend = Collections.singletonList(toSend.getSimpleMail());
        Operation sendOperation = new Operation(mailboxOwner, Operation.OP_SEND, Operation.NO_ERR, mailsToSend);
        Operation response = executeOperation(sendOperation);

        if (response == null)
            return "Error coudn't talk to server";
        else if(!response.error().equals(Operation.NO_ERR))
            return "Server response: " + response.error();
        return null;
    }
}
