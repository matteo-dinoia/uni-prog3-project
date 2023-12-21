package backend;

import com.google.gson.Gson;
import interfaces.Logger;
import model.operationData.Operation;
import model.operationData.SimpleMail;
import java.io.*;
import java.net.Socket;
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

    @Override
    public void run() {
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
            } catch (IOException e) {
                logger.log("ERROR: could not close socket");
            }
        }
        logger.log("ENDED");
    }

    private Operation replyInternal(Operation op) throws IOException {
        return switch (op.operation()){
            case Operation.OP_GETALL -> getAll(op);
            case Operation.OP_SEND -> sendMail(op);
            case Operation.OP_DELETE -> deleteMail(op);
            default -> getNew(op);
        };
    }

    private Operation getAll(Operation op) {
        logger.log("INITIALIZED CONNECTION -> RESPONDING WITH ALL MAILS");
        FileManager senderFile = FileManager.get("mail/" + op.mailboxOwner() + ".json", logger);
        List<SimpleMail> mails = senderFile.readMails();

        if(mails == null)
            return null;
        int nop = mails.isEmpty() ? 0 : mails.getLast().id() + 1;
        return new Operation("test", nop, Operation.NO_ERR, mails);
    }

    private Operation getNew(Operation op) {
        logger.log("INITIALIZED CONNECTION -> RESPONDING NEW MAIL");
        FileManager senderFile = FileManager.get("mail/" + op.mailboxOwner() + ".json", logger);
        List<SimpleMail> mails = senderFile.readMails();

        if(mails == null)
            return null;
        int nop = mails.isEmpty() ? 0 : mails.getLast().id() + 1;
        return new Operation("test", nop, Operation.NO_ERR,
                    mails.stream().filter((mail) -> mail.id() >= op.operation()).toList());
    }


    private Operation sendMail(Operation op) {
        logger.log("INITIALIZED CONNECTION -> SAVING NEW MAIL");

        FileManager senderFile = FileManager.get("mail/" + op.mailboxOwner() + ".json", logger);
        for(SimpleMail toSend : op.mailList()){
            FileManager receiverFile = FileManager.get("mail/" + toSend.destinations() + ".json", logger);
            boolean statusRes = senderFile.appendMails(Collections.singletonList(toSend));
            statusRes &= receiverFile.appendMails(Collections.singletonList(toSend));

            if(!statusRes){
                senderFile.removeMails(Collections.singletonList(toSend));
                receiverFile.removeMails(Collections.singletonList(toSend));
                return null;
            }
        }

        return new Operation("test", op.operation(), Operation.NO_ERR, op.mailList());
    }


    private Operation deleteMail(Operation op) {
        logger.log("INITIALIZED CONNECTION -> REMOVE EXISTING MAIL");
        FileManager senderFile = FileManager.get("mail/" + op.mailboxOwner() + ".json", logger);
        boolean statusRes = senderFile.removeMails(op.mailList());

        if(statusRes)
            return new Operation("test", op.operation(), Operation.NO_ERR, op.mailList());
        return null;
    }
}
