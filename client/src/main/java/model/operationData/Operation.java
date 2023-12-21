package model.operationData;

import java.util.List;

public record Operation (String mailboxOwner, int operation, String error, List<SimpleMail> mailList){
    public static final String NO_ERR = "";
    public static final int OP_GETALL = 0;
    public static final int OP_SEND_SINGLEMAIL = -1;
    public static final int OP_DELETE = -2;

    public Operation getErrorResponse(String errorStr){
        return new model.operationData.Operation(mailboxOwner(), operation(), errorStr, mailList());
    }

    public Operation getValidResponse(int operationCode, List<SimpleMail> mails){
        return new model.operationData.Operation(mailboxOwner(), operationCode, NO_ERR, mails);
    }
}
