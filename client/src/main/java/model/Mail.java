package model;

import javafx.beans.property.SimpleStringProperty;
import java.io.Serializable;

public class Mail implements Serializable {
    private final SimpleStringProperty source = new SimpleStringProperty("");
    private final SimpleStringProperty destinations = new SimpleStringProperty("");
    private SimpleStringProperty object = new SimpleStringProperty("");
    private SimpleStringProperty content = new SimpleStringProperty("");
    //TODO USE THEM
    private int id;
    private long timestamp;

    public Mail(String from, String firstDest, String object, String content){
        this.source.set(from);
        this.destinations.set(firstDest);
        this.object.set(object);
        this.content.set(content);
    }

/*  public boolean checkValidity(){ // move out
        if(!source.checkValidity())
            return false;

        for(MailAddress addr : destinations){
            if(addr == null || !addr.checkValidity())
                return false;
        }

        return true;
    }*/

    @Override public String toString() {
        return object.get();
    }

    public String formatted(){
        return "From: " + getFromProperty() + "\n" +
                "To: " + getToProperty() + "\n" +
                "Obj: " + getObjectProperty() + "\n" +
                "\n" + getContentProperty() + "\n";
    }

    public SimpleStringProperty getFromProperty(){ return source; }
    public SimpleStringProperty getObjectProperty(){ return object; }
    public SimpleStringProperty getContentProperty(){ return content; }
    public SimpleStringProperty getToProperty(){ return destinations; }


    String getFrom() {
        return source.get();
    }
}
