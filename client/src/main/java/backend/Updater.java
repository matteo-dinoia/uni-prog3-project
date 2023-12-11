package backend;

import model.Mail;
import com.google.gson.Gson;
import model.MailBox;

import java.io.*;
import java.net.Socket;
import java.util.List;

// TODO need to be scheduled
public class Updater implements Runnable{
    private MailBox mailBox;
    private int lastUpdate = -1;

    public Updater(MailBox mailBox){
        this.mailBox = mailBox;
    }

    @Override  public void run() {
        System.out.println("Updating");

        // TODO actual update
        if(lastUpdate == -1){
            tryConnection();
            lastUpdate = 0;
        }
    }

    private void tryConnection() {
        Gson gson = new Gson();
        try(Socket socket = new Socket("localhost", 60421);){
            try(Reader input = new InputStreamReader(socket.getInputStream());){
                Mail[] m;
                m = gson.fromJson(input, Mail[].class);

                mailBox.add(List.of(m));
            }catch (IOException exc){
                System.err.println("ERROR: during communication with client");
            }
        } catch (IOException e) {
            System.err.println("ERROR: during initialization of connection");
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }

    }



}
