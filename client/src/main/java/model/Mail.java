package model;

import backend.SimpleMail;
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

    public Mail(String from, String dest, String object, String content){
        this.source.set(from);
        this.destinations.set(dest);
        this.object.set(object);
        this.content.set(content);
    }

    public Mail(SimpleMail toAdd) {
        this(toAdd.getSource(), toAdd.getDestinations(),
                toAdd.getObject(), toAdd.getContent());
    }

    @Override public String toString() {
        return object.get();
    }

    public String formatted(){
        return "From: " + getFromProperty().get() + "\n" +
                "To: " + getToProperty().get() + "\n" +
                "Obj: " + getObjectProperty().get() + "\n" +
                "\n" + getContentProperty().get() + "\n";
    }

    public SimpleStringProperty getFromProperty(){ return source; }
    public SimpleStringProperty getObjectProperty(){ return object; }
    public SimpleStringProperty getContentProperty(){ return content; }
    public SimpleStringProperty getToProperty(){ return destinations; }


    String getFrom() { return source.get(); }
    String getTo() { return destinations.get(); }
}
