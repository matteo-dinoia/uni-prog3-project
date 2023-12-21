package backend;

import model.Mail;
import model.MailValidator;
import model.operationData.Operation;
import model.operationData.SimpleMail;
import java.util.Collections;
import java.util.List;

public class Sender extends ServiceRequester<String> {
    private final Mail toSend;
    private final MailValidator validator = new MailValidator();

    public Sender(Mail toSend){
        this.toSend = toSend;
    }

    // Return error string -> if null then it succeded
    @Override public String call() {
        if(toSend == null || !validator.checkValidity(toSend))
            return "Mail not valid";

        List<SimpleMail> mailsToSend = Collections.singletonList(toSend.getSimpleMail());
        Operation sendOperation = new Operation(toSend.getFrom(), Operation.OPERATION_SEND, mailsToSend);
        Operation response = executeOperation(sendOperation);

        if (response == null)
            return "Error during communication with server";
        return null;
    }
}
