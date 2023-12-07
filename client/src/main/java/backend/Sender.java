package backend;

import model.Mail;

public class Sender {

    public void send(Mail mail){
        if(mail == null || !mail.checkValidity())
            System.out.println("Mail not valid");
        else
            System.out.println("Sending mail:\n" + mail.formatted() + "\n\n");
    }
}
