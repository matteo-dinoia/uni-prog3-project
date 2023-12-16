package backend;

import model.Mail;
import com.google.gson.Gson;
import model.MailBox;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;
import java.util.List;

import java.io.*;
import java.net.Socket;
import java.util.List;

// TODO need to be scheduled
public class Updater implements Runnable{
    private final MailBox mailBox;
    private int lastUpdate = -1;

    public Updater(MailBox mailBox){
        this.mailBox = mailBox;
    }

    @Override  public void run() {
        mailBox.setOnline(tryConnection());
    }

    private boolean tryConnection() {
        // TODO actual update
        Gson gson = new Gson();
        try(Socket socket = new Socket("localhost", 60421)){
            try(Reader input = new InputStreamReader(socket.getInputStream())){
                if(lastUpdate != -1) return true;

                Mail[] m;
                m = gson.fromJson(input, Mail[].class);

                mailBox.add(List.of(m));
                lastUpdate = 0;
                return true;
            }catch (IOException exc){
                System.err.println("ERROR: during communication with client");
            }
        } catch (IOException e) {
            // Do nothing because there it is a internet problem
        }catch (Throwable throwable){
            System.err.println("ERROR: while manipulating data: " + throwable.getMessage()); //TODO Remove
        }

        return false;
    }



}
