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
            if(addr == null || !addr.checkValidity())
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
        if(source != null) res.append(source);
        res.append("\n");

        res.append("To: ");
        for(MailAddress addr : destinations){
            res.append(addr.toString() == null ? "" : addr.toString());
            res.append(", ");
        }
        res.append("\n");

        res.append("Obj: ");
        if(object != null) res.append(object);
        res.append("\n\n");

        if(content != null) res.append(content);
        res.append("\n");

        return res.toString();
    }


}
