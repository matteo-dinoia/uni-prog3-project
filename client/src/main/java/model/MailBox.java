package model;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class MailBox {
    public static MailBox mBoxTmp = null;
    // Field
    private final String owner;
    private final SimpleBooleanProperty online = new SimpleBooleanProperty(false);
    private final ObservableList<Mail> listReceived = FXCollections.observableArrayList();
    private final ObservableList<Mail> listSent = FXCollections.observableArrayList();

    public MailBox(String owner){
        this.owner = owner;
    }

    public ObservableList<Mail> getObservableListSent(){ return listSent; }
    public ObservableList<Mail> getObservableListReceived(){ return listReceived; }
    public String getOwner() { return owner; }
    public SimpleBooleanProperty getOnlineProperty(){ return online; }

    public void add(Mail mail) {
        if(mail == null)
            return;

        if(owner.equals(mail.getFrom())) {
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

    public void setOnline(boolean value) {
        Platform.runLater(() -> online.set(value));
    }
}
