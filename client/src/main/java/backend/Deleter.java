package backend;

import model.Mail;

public class Deleter extends ServiceRequester<String>{
    private final Mail toDelete;

    public Deleter(Mail mail) {
        this.toDelete = mail;
    }

    // Return error string -> if null then it succeded
    @Override public String call() {
        return null;
    }
}
