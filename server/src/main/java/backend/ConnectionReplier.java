package backend;

import com.google.gson.Gson;
import interfaces.Logger;
import model.operationData.Operation;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
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
        logger.log("INITIALIZED CONNECTION");
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
            System.err.println("ERROR: while manipulating data: " + throwable.getMessage()); //TODO Remove
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.log("ERROR: could not close socket");
            }
        }
        logger.log("ENDED");
    }

    private Operation testOperation() {
        writeHardcodedTestData();

        try (Reader reader = new InputStreamReader(new FileInputStream("mail/file.json"))) {
            return gson.fromJson(reader, Operation.class);
        } catch (IOException e) {
            logger.log("ERROR: coudn't read from file");
        }

        return new Operation("test", -1, new ArrayList<>());
    }

    private void writeHardcodedTestData() {
        /*try(Writer writer = new OutputStreamWriter(new FileOutputStream("mail/file.json"))){
            SimpleMail[] mails = {
                    new SimpleMail("testuser@gmail.com", "a", "b1", "c"),
                    new SimpleMail("testuser@gmail.com", "a", "b2", "c"),
                    new SimpleMail("a", "a", "bb1", "c"),
                    new SimpleMail("a", "a", "bb2", "c"),
                    new SimpleMail("a", "a", "bb3", "c"),
                    new SimpleMail("a", "a", "bb4", "c"),
            };
            writer.write(gson.toJson(new Operation("test", mails.length, List.of(mails))));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private Operation replyInternal(Operation op) throws IOException {
        Operation response = null;

        if (op.operation() == Operation.OPERATION_GETALL)
            response = getAll();
        else if (op.operation() == 1)
            response = getNew();
        else if (op.operation() == Operation.OPERATION_SEND)
            response = receiveMail(op);
        else
            logger.log("ERROR: invalid operation");

        return response;
    }

    private Operation getAll() {
        logger.log("RESPONDING WITH ALL MAILS");
        return testOperation();
    }

    private Operation getNew() {
        logger.log("RESPONDING NOTHING.");
        return new Operation("test", 0, new ArrayList<>());
    }


    private Operation receiveMail(Operation incomingOp) {
        File file = new File("mail/file.json");
        Operation fileOperation;

        if (file.exists() && file.length() > 0) {
            try (Reader reader = new InputStreamReader(new FileInputStream(file))) {
                fileOperation = gson.fromJson(reader, Operation.class);
                if (fileOperation == null) {
                    fileOperation = new Operation("test", 6, new ArrayList<>());
                }
            } catch (IOException e) {
                logger.log("ERROR: couldn't read from file");
                return null;
            }
        } else {
            fileOperation = new Operation("test", 6, new ArrayList<>());
        }

        fileOperation.mailList().addAll(incomingOp.mailList());

        try (Writer w = new OutputStreamWriter(new FileOutputStream(file))) {
            gson.toJson(fileOperation, w);
        } catch (IOException e) {
            logger.log("ERROR: couldn't write to file");
            return null;
        }

        return new Operation("test", 0, new ArrayList<>());
    }


    private void deleteMail(Operation op) {

    }
}
