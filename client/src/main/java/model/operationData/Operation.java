package model.operationData;

import java.util.List;

public record Operation (String mailboxOwner, int operation, List<SimpleMail> mailList){
    public static final int OPERATION_GETALL = 0;
}
