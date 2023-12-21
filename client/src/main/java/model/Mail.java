package model;

import javafx.beans.property.SimpleStringProperty;
import model.operationData.SimpleMail;
import java.io.Serializable;
import java.util.Date;

public class Mail implements Serializable {
    private final SimpleStringProperty source = new SimpleStringProperty("");
    private final SimpleStringProperty destinations = new SimpleStringProperty("");
    private final SimpleStringProperty object = new SimpleStringProperty("");
    private final SimpleStringProperty content = new SimpleStringProperty("");
    //TODO USE THEM
    private int id = -1;
    private Date date = null;

    public Mail(String from, String dest, String object, String content){
        this.source.set(from == null ? "" : from);
        this.destinations.set(dest == null ? "" : dest);
        this.object.set(object == null ? "" : object);
        this.content.set(content == null ? "" : content);
    }

    public Mail(SimpleMail toAdd) {
        this(toAdd.source(), toAdd.destinations(),
                toAdd.object(), toAdd.content());
        this.id = toAdd.id();
        this.date = toAdd.date();
    }

    public Mail(Mail mail) {
        if(mail == null) return;

        this.source.set(mail.source.get());
        this.destinations.set(mail.destinations.get());
        this.object.set(mail.object.get());
        this.content.set(mail.content.get());
        this.id = mail.id;
        this.date = mail.date;
    }

    public SimpleMail getSimpleMail(){
        return new SimpleMail(getFrom(), getTo(), getObject(), getContent(), id, date);
    }

    @Override public String toString() {
        return object.get();
    }

    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;

        Mail mail = (Mail) o;
        return this.getFrom().equals(mail.getFrom()) && this.getTo().equals(mail.getTo())
                && this.getObject().equals(mail.getObject()) && this.getContent().equals(mail.getContent());
    }

    public String formatted(){
        return "From: " + fromProperty().get() + "\n" +
                "To: " + toProperty().get() + "\n" +
                "Obj: " + objectProperty().get() + "\n" +
                "\n" + contentProperty().get() + "\n";
    }

    public SimpleStringProperty fromProperty(){ return source; }
    public SimpleStringProperty objectProperty(){ return object; }
    public SimpleStringProperty contentProperty(){ return content; }
    public SimpleStringProperty toProperty(){ return destinations; }


    public String getFrom() { return source.get(); }
    public String getTo() { return destinations.get(); }
    public String getObject() { return object.get(); }
    public String getContent() { return content.get(); }

    void setId(int id){ this.id = id; }
    int getId(){ return this.id; }
    void setDate(Date date){ this.date = date; }
    Date getDate(){ return date; }
}
