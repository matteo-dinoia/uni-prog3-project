package backend;

import model.Mail;
import model.MailValidator;

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

        System.out.println("Sending mail:\n" + toSend.formatted() + "\n\n");
        return null;
    }
}
