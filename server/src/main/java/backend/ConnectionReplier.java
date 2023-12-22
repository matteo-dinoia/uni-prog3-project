package backend;

import backend.util.LoggableError;
import com.google.gson.Gson;
import interfaces.Logger;
import model.operationData.Operation;
import model.operationData.SimpleMail;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ConnectionReplier implements Runnable {
    private final Logger logger;
    private final Socket socket;
    private final Gson gson = new Gson();

    public ConnectionReplier(Logger logger, Socket socket) {
        this.socket = socket;
        this.logger = logger;
    }

    private Operation getErrorAndLog(Operation toRespond, String errorStr){
        logger.log("ERROR", errorStr);
        return toRespond.getErrorResponse(errorStr);
    }

    @Override public void run() {
        try (Scanner scanner = new Scanner(socket.getInputStream())) {
            try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
                String s = scanner.nextLine();
                Operation op = gson.fromJson(s, Operation.class);

                Operation response;
                try{
                    response = replyInternal(op);
                } catch (LoggableError loggableError){
                    response = getErrorAndLog(op, loggableError.getMessage());
                }
                writer.println(gson.toJson(response));

                logger.log("INFO", "Ending connection with " + op.mailboxOwner());
            }
        } catch (IOException exc) {
            logger.log("ERROR", "During communication with client");
        } catch (Throwable throwable) {
            logger.log("DEBUG", "While manipulating data: " + throwable.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) { logger.log("ERROR", "Could not close socket"); }
        }

    }

    private Operation replyInternal(Operation op) {
        try{
            MailboxPersistencyManager senderMailbox = MailboxPersistencyManager.get(op.mailboxOwner());

            return switch (op.operation()){
                case Operation.OP_SEND_SINGLEMAIL -> sendMail(senderMailbox, op);
                case Operation.OP_DELETE -> deleteMail(senderMailbox, op);
                default -> getNew(senderMailbox, op);
            };
        }catch (NullPointerException exc){
            throw new LoggableError("One of the request operation field is null");
        }
    }

    private Operation getNew(MailboxPersistencyManager senderMailbox, Operation op) {
        logger.log("INFO", "Intialized connection with " + op.mailboxOwner() + " -> responding with new mail/s with id >= " + op.operation());
        List<SimpleMail> mails = senderMailbox.readMails();

        final int nop = MailboxPersistencyManager.getNextOp(mails);
        final List<SimpleMail> filtered = mails.stream().filter((mail) -> mail.id() >= op.operation()).toList();
        return op.getValidResponse(nop, filtered);
    }


    private Operation sendMail(MailboxPersistencyManager senderMailbox, Operation op) {
        logger.log("INFO", "Intialized connection with " + op.mailboxOwner() + "-> saving single mail sent");

        if(op.mailList() == null || op.mailList().size() != 1)
            throw new LoggableError("Wrong number of mail to send (!= 1)");
        SimpleMail toSend = op.mailList().getFirst();
        if(toSend.destinations() == null || toSend.source() == null)
            throw new LoggableError("Source or destination is null");

        // File management and error checking
        List<MailboxPersistencyManager> receiversMailbox = new ArrayList<>();

        String[] destNames = toSend.destinations().split(",");
        for(String dest : destNames){
            MailboxPersistencyManager tmp = MailboxPersistencyManager.get(dest.trim());
            receiversMailbox.add(tmp);
        }

        // Actually send
        senderMailbox.appendMails(Collections.singletonList(toSend));
        for(MailboxPersistencyManager tmp : receiversMailbox){
            tmp.appendMails(Collections.singletonList(toSend));
        }

        logger.log("INFO", "Sent mail by " + op.mailboxOwner() + ": \"" + op.mailList().getFirst().object() + "\"");
        return op.getValidResponse(op.operation(), op.mailList());
    }


    private Operation deleteMail(MailboxPersistencyManager senderMailbox, Operation op) {
        logger.log("INFO", "Intialized connection with " + op.mailboxOwner() + "-> remove " + op.mailList().size() + " mail/s");
        senderMailbox.removeMails(op.mailList());

        return op.getValidResponse(op.operation(), op.mailList());

    }
}
