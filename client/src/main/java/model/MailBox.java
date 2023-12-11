package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.List;

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
    public String getOwner() { return owner.toString(); }

    public void add(Mail mail) {
        if(mail == null)
            return;

        if(owner.toString().equals(mail.getFrom())) {
            if(!listSent.contains(mail))
                listSent.add(mail);
        } else{
            if(!listReceived.contains(mail))
                listReceived.add(mail);
        }

    }

    public void add(List<Mail> list) {
        if(list == null || list.isEmpty())
            return;

        for(Mail toAdd : list)
            this.add(toAdd);
    }
}
