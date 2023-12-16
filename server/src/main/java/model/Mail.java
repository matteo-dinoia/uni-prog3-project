package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Mail implements Serializable {
    private final MailAddress source;
    private final List<MailAddress> destinations = new ArrayList<>();
    private final String object;
    private final String content;
    //TODO USE THEM
    private int id;
    private long timestamp;

    public Mail(String from, String firstDest, String object, String content){
        source = new MailAddress(from);
        addDestination(firstDest);
        this.object = object;
        this.content = content;
    }

    public void addDestination(String to){
        destinations.add(new MailAddress(to));
    }

    public boolean checkValidity(){
        if(!source.checkValidity())
            return false;

        for(MailAddress addr : destinations){
            if(addr == null || !addr.checkValidity())
                return false;
        }

        return true;
    }

    @Override public String toString() {
        return object;
    }

    public String formatted(){
        return "From: " + getFrom() + "\n" +
                "To: " + getTo() + "\n" +
                "Obj: " + getObject() + "\n" +
                "\n" + getContent() + "\n";
    }

    public String getFrom(){ return source != null ? source.toString() : ""; }
    public String getObject(){ return object != null ? object : ""; }
    public String getContent(){ return content != null ? content : ""; }
    public String getTo(){
        StringBuilder res = new StringBuilder();

        for(MailAddress addr : destinations){
            if(addr != null && addr.toString() != null){
                res.append(addr);
                res.append(", ");
            }
        }

        return res.toString();
    }


}
