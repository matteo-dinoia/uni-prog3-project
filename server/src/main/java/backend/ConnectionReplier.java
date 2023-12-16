package backend;

import interfaces.Logger;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import model.Mail;

public class ConnectionReplier implements Runnable{
    private final Logger logger;
    private Socket socket;
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

    private Mail[] testMails(){
        try(Reader reader = new InputStreamReader(new FileInputStream("mail/file.json"))){
            return gson.fromJson(reader, Mail[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Mail[0];
    }
}
