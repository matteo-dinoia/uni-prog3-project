package backend;

import com.google.gson.Gson;
import interfaces.Logger;
import model.operationData.Operation;
import model.operationData.SimpleMail;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnectionReplier implements Runnable{
    private final Logger logger;
    private final Socket socket;
    private final Gson gson = new Gson();

    public ConnectionReplier(Logger logger, Socket socket) {
        this.socket = socket;
        this.logger = logger;
    }


    @Override public void run() {
        try(Writer output = new OutputStreamWriter(socket.getOutputStream());){
            logger.log("START COMMUNITCATION WITH CLIENT");
            String s = gson.toJson(testOperation());
            logger.log("RESPONDING (num char: " + s.length() + ")");
            output.write(s);
        }catch (IOException exc){
            logger.log("ERROR: during communication with client");
        } catch (Throwable throwable){
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
}
