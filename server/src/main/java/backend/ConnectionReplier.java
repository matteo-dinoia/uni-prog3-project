package backend;

import interfaces.Logger;

import java.io.*;
import java.net.Socket;

import com.google.gson.Gson;
import model.SimpleMail;

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
            String s = gson.toJson(testMails());
            logger.log("RESPONDING (num char: " + s.length() + ")");
            output.write(s);
        }catch (IOException exc){
            logger.log("ERROR: during communication with client");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.log("ERROR: could not close socket");
            }
        }
        logger.log("ENDED");
    }

    private SimpleMail[] testMails(){
        /*try(Writer writer = new OutputStreamWriter(new FileOutputStream("mail/file.json"))){
            SimpleMail[] mails = {
                    new SimpleMail("testuser@gmail.com", "a", "b1", "c"),
                    new SimpleMail("testuser@gmail.com", "a", "b2", "c"),
                    new SimpleMail("a", "a", "bb1", "c"),
                    new SimpleMail("a", "a", "bb2", "c"),
                    new SimpleMail("a", "a", "bb3", "c"),
                    new SimpleMail("a", "a", "bb4", "c"),
            };
            writer.write(gson.toJson(mails));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try(Reader reader = new InputStreamReader(new FileInputStream("mail/file.json"))){
            return gson.fromJson(reader, SimpleMail[].class);
        } catch (IOException e) {
            logger.log("ERROR: coudn't read from file");
        }

        return new SimpleMail[0];
    }
}
