package model;

public class MailValidator {
    public boolean checkValidity(Mail mail){
        if(mail == null || !isAddressValid(mail.getFrom()))
            return false;

        if(mail.getObject().isEmpty() || mail.getObject().isBlank())
            return false;

        String[] destinations = mail.getTo().split(",");
        if(destinations.length == 0) return false;

        for(String dest : destinations){
            if(!isAddressValid(dest)) return false;
        }

        return true;
    }

    public boolean isAddressValid(String address){
        if(address == null || address.isEmpty())
            return false;
        address = address.trim();

        final int START = 0;
        final int AFTER_AT = 1;
        final int ACCEPTED = 2;

        int state = START;

        for(char c : address.toLowerCase().toCharArray()){
            if(c == '@'){
                if(state > START)
                    return false;
                state = AFTER_AT;
            }else if(c == '.'){
                if(state == AFTER_AT)
                    state = ACCEPTED;
            } else if(!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z'))){
                return false;
            }
        }

        return state == ACCEPTED;
    }

}
