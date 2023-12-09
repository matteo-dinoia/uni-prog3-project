package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MailBox {
    // Field
    private final MailAddress owner;
    private final ObservableList<Mail> listReceived = FXCollections.observableArrayList();
    private final ObservableList<Mail> listSent = FXCollections.observableArrayList();

    public MailBox(MailAddress owner){
        this.owner = owner;
    }

    public ObservableList<Mail> getObservableListSent(){ return listSent; }
    public ObservableList<Mail> getObservableListReceived(){ return listReceived; }
    public void addSent(Mail mail) { listSent.add(mail); }
    public void addRecieved(Mail mail) { listReceived.add(mail); }
    public String getOwner() { return owner.toString(); }
}
