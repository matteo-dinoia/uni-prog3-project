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

        String[] splitAt = address.split("@");
        if(splitAt.length != 2) return false;

        String[] splitDot = splitAt[1].split("\\.");
        if(splitDot.length < 2) return false;

        boolean res = isOnlyLetterOrNumber(splitAt[0]);
        for(String s : splitDot)
            res &= isOnlyLetterOrNumber(s);

        return res;
    }

    private boolean isOnlyLetterOrNumber(String str){
        if(str == null || str.isEmpty()) return false;

        str = str.toLowerCase();
        for(char c : str.toCharArray()){
            if(!isOnlyLetterOrNumber(c)) return false;
        }

        return true;
    }

    private boolean isOnlyLetterOrNumber(char c){ return (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9'); }
}
