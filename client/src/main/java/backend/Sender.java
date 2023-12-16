package backend;

import model.Mail;
import model.MailValidator;

public class Sender implements Runnable{
    private final Mail toSend;
    private final MailValidator validator = new MailValidator();

    public Sender(Mail toSend){
        this.toSend = toSend;
    }

    public void run(){
        if(toSend == null || !validator.checkValidity(toSend))
            System.out.println("Mail not valid");
        else
            System.out.println("Sending mail:\n" + toSend.formatted() + "\n\n");
    }
}
