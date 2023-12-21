package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.operationData.SimpleMail;
import java.util.List;

public class MailBox {
    // Field
    private final Mail selectedMail = new Mail("", "", "", "");
    private final SimpleBooleanProperty selectionExist = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty online = new SimpleBooleanProperty(false);
    // --------
    private final String owner;
    private final ObservableList<Mail> listReceived = FXCollections.observableArrayList();
    private final ObservableList<Mail> listSent = FXCollections.observableArrayList();

    public MailBox(String owner){
        this.owner = owner;
    }
    public String getOwner() { return owner; }


    public ObservableList<Mail> observableListSent(){ return listSent; }
    public ObservableList<Mail> observableListReceived(){ return listReceived; }
    public SimpleBooleanProperty onlineProperty(){ return online; }
    public SimpleBooleanProperty selectionExistProperty(){ return selectionExist; }

    public Mail getSelectedMail(){ return selectedMail; }
    public void setSelectedMail(Mail mail){
        if(mail == null){
            mail = new Mail("", "", "", "");
            selectionExist.set(false);
        }else {
            selectionExist.set(true);
        }

        selectedMail.fromProperty().bind(mail.fromProperty());
        selectedMail.toProperty().bind(mail.toProperty());
        selectedMail.objectProperty().bind(mail.objectProperty());
        selectedMail.contentProperty().bind(mail.contentProperty());
        selectedMail.timeProperty().bind(mail.timeProperty());
        selectedMail.setId(mail.getId());
    }

    public void add(Mail mail) {
        if(mail == null) return;

        if(owner.equals(mail.getFrom())) {
            if(!listSent.contains(mail))
                listSent.add(mail);
        } else{
            if(!listReceived.contains(mail))
                listReceived.add(mail);
        }
    }

    public void add(List<SimpleMail> list) {
        if(list == null || list.isEmpty())
            return;

        for(SimpleMail toAdd : list)
            this.add(new Mail(toAdd));
    }

    public void remove(Mail mail){
        if(mail == null) return;

        if(mail.equals(selectedMail)) setSelectedMail(null);

        if(owner.equals(mail.getFrom()))
            listSent.remove(mail);
        else
            listReceived.remove(mail);
    }

    public void setOnline(boolean value) { online.setValue(value); }
}
