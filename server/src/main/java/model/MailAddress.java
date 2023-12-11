package model;

import java.io.Serializable;

public class MailAddress implements Serializable {
    private final String address;

    public MailAddress(String address){
        this.address = address;
    }

    @Override public String toString() { return address; }

    public boolean checkValidity(){
        if(address == null || address.isEmpty())
            return false;

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
