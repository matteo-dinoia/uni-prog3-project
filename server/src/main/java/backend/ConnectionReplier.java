package backend;

import com.google.gson.Gson;
import interfaces.Logger;
import model.operationData.Operation;
import model.operationData.SimpleMail;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConnectionReplier implements Runnable{
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
                replyInternal(scanner, writer);
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

    private Operation testOperation(){
        writeHardcodedTestData();

        try(Reader reader = new InputStreamReader(new FileInputStream("mail/file.json"))){
            return gson.fromJson(reader, Operation.class);
        } catch (IOException e) {
            logger.log("ERROR: coudn't read from file");
        }

        return new Operation("test", -1, new ArrayList<>());
    }

    private void writeHardcodedTestData(){
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

    private void replyInternal(Scanner scanner, PrintWriter writer) throws IOException {
        String s = scanner.nextLine();
        Operation op = gson.fromJson(s, Operation.class);

        if (op.operation() == Operation.OPERATION_GETALL)
            getAll(writer);
        else if (op.operation() > 0)
            getNew(writer);
        else
            logger.log("ERROR: invalid operation");
    }

    private void getAll(PrintWriter writer){
        String res = gson.toJson(testOperation());
        logger.log("RESPONDING (num char: " + res.length() + ")");
        writer.println(res);
    }

    private void getNew(PrintWriter writer){
        String res = gson.toJson(new Operation("test", 0, new ArrayList<>()));
        logger.log("RESPONDING NOTHING.");
        writer.println(res);
    }
}
