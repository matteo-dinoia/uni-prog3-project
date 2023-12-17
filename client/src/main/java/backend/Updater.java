package backend;

import com.google.gson.Gson;
import javafx.application.Platform;
import model.MailBox;
import model.operationData.Operation;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

// TODO need to be scheduled
public class Updater implements Runnable{
    private final MailBox mailBox;
    private int lastUpdate = Operation.OPERATION_GETALL;
    private final Gson gson = new Gson();

    public Updater(MailBox mailBox){
        this.mailBox = mailBox;
    }

    @Override  public void run() {
        boolean online = updateData();
        Platform.runLater(() -> mailBox.setOnline(online));
    }

    private boolean updateData() {
        // TODO actual update
        try(Socket socket = new Socket("localhost", 60421)){
            try(PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)){
                try(Scanner scanner = new Scanner(socket.getInputStream())){
                    updateDataInternal(scanner, writer);
                    return true;
                }
            }catch (IOException exc) {
                System.err.println("ERROR: during communication with server: " + exc.getMessage());
            }
        } catch (IOException e) {
            // Do nothing because there it is a internet problem
        }catch (Throwable throwable){
            System.err.println("ERROR: while manipulating data: " + throwable.getMessage()); //TODO Remove
        }

        return false;
    }



}
