package backend;

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
        logger.log("ERROR:" + errorStr);
        return toRespond.getErrorResponse(errorStr);
    }

    @Override public void run() {
        try (Scanner scanner = new Scanner(socket.getInputStream())) {
            try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
                String s = scanner.nextLine();
                Operation op = gson.fromJson(s, Operation.class);
                Operation response = replyInternal(op);
                writer.println(gson.toJson(response));
            }
        } catch (IOException exc) {
            logger.log("ERROR: during communication with client");
        } catch (Throwable throwable) {
            System.err.println("ERROR: while manipulating data: " + throwable.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) { logger.log("ERROR: could not close socket"); }
        }
        logger.log("ENDED");
    }

    private Operation replyInternal(Operation op) {
        FileManager senderMailbox = FileManager.get(op.mailboxOwner());
        if(senderMailbox == null)
            return getErrorAndLog(op, "Unauthorized access");


        return switch (op.operation()){
            case Operation.OP_GETALL -> getAll(senderMailbox, op);
            case Operation.OP_SEND_SINGLEMAIL -> sendMail(senderMailbox, op);
            case Operation.OP_DELETE -> deleteMail(senderMailbox, op);
            default -> getNew(senderMailbox, op);
        };
    }

    private Operation getAll(FileManager senderMailbox, Operation op) {
        logger.log("INITIALIZED CONNECTION -> RESPONDING WITH ALL MAILS");
        List<SimpleMail> mails = senderMailbox.readMails();
        if(mails == null) return getErrorAndLog(op, "Internal Server Error getting all");

        final int nop = FileManager.getNextOp(mails);
        return op.getValidResponse(nop, mails);
    }

    private Operation getNew(FileManager senderMailbox, Operation op) {
        logger.log("INITIALIZED CONNECTION -> RESPONDING NEW MAIL");
        List<SimpleMail> mails = senderMailbox.readMails();
        if(mails == null) return getErrorAndLog(op, "Internal Server Error getting new");

        final int nop = FileManager.getNextOp(mails);
        final List<SimpleMail> filtered = mails.stream().filter((mail) -> mail.id() >= op.operation()).toList();
        return op.getValidResponse(nop, filtered);
    }


    private Operation sendMail(FileManager senderMailbox, Operation op) {
        logger.log("INITIALIZED CONNECTION -> SAVING NEW MAIL");

        if(op.mailList() == null || op.mailList().size() != 1)
            return getErrorAndLog(op, "Wrong number of mail to send (!= 1)");
        SimpleMail toSend = op.mailList().getFirst();

        // File management and error checking
        List<FileManager> receiversMailbox = new ArrayList<>();
        if(toSend.destinations() == null || toSend.source() == null)
            return getErrorAndLog(op, "Source or destination is null");

        String[] destNames = toSend.destinations().split(",");
        for(String dest : destNames){
            FileManager tmp = FileManager.get(dest);
            if(tmp == null) return getErrorAndLog(op, "Not existent destination/s");
            receiversMailbox.add(tmp);
        }

        // Actually send
        boolean statusRes = senderMailbox.appendMails(Collections.singletonList(toSend));
        for(FileManager tmp : receiversMailbox){
            statusRes &= tmp.appendMails(Collections.singletonList(toSend));
        }

        if(!statusRes)
            return getErrorAndLog(op,"Internal Server Error sending mail");
        return new Operation("test", op.operation(), Operation.NO_ERR, op.mailList());
    }


    private Operation deleteMail(FileManager senderMailbox, Operation op) {
        logger.log("INITIALIZED CONNECTION -> REMOVE EXISTING MAIL");
        boolean statusRes = senderMailbox.removeMails(op.mailList());

        if(!statusRes)
            return getErrorAndLog(op, "Couldn't delete mail (maybe it doesn't exist)");
        return op.getValidResponse(op.operation(), op.mailList());

    }
}
