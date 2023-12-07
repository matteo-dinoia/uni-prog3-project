package model;

import java.util.ArrayList;
import java.util.List;

public class Mail {
    private final MailAddress source;
    private final List<MailAddress> destinations = new ArrayList<>();
    private final String object;
    private final String content;

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
            if(!addr.checkValidity())
                return false;
        }

        return true;
    }

    @Override public String toString() {
        return object;
    }

    public String formatted(){
        StringBuilder res = new StringBuilder();

        res.append("From: ");
        res.append(getFrom());
        res.append("\n");

        res.append("To: ");
        res.append(getTo());

        res.append("\n");

        res.append("Obj: ");
        res.append(getObject());
        res.append("\n\n");

        res.append(getContent());
        res.append("\n");

        return res.toString();
    }

    public String getFrom(){ return source != null ? source.toString() : ""; }
    public String getObject(){ return object != null ? object : ""; }
    public String getContent(){ return content != null ? content : ""; }
    public String getTo(){
        StringBuilder res = new StringBuilder();

        for(MailAddress addr : destinations){
            if(addr != null && addr.toString() != null){
                res.append(addr.toString());
                res.append(", ");
            }
        }

        return res.toString();
    }


}
