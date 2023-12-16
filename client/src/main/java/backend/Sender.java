package backend;

import model.Mail;

public class Sender implements Runnable{
    private final Mail toSend;

    public Sender(Mail toSend){
        this.toSend = toSend;
    }

    public void run(){
        if(toSend == null/* || !toSend.checkValidity()*/)
            System.out.println("Mail not valid");
        else
            System.out.println("Sending mail:\n" + toSend.formatted() + "\n\n");
    }
}
