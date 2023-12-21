package model.operationData;

import java.util.List;

//TOD USE ERROR
public record Operation (String mailboxOwner, int operation, String error, List<SimpleMail> mailList){
    public static final String NO_ERR = "";
    public static final int OP_GETALL = 0;
    public static final int OP_SEND = -1;
    public static final int OP_DELETE = -2;
}