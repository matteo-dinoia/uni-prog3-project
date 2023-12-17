package backend;

import com.google.gson.Gson;
import javafx.application.Platform;
import model.MailBox;
import model.operationData.Operation;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;

// TODO need to be scheduled
public class Updater implements Runnable{
    private final MailBox mailBox;
    private int lastUpdate = -1;

    public Updater(MailBox mailBox){
        this.mailBox = mailBox;
    }

    @Override  public void run() {
        boolean online = updateData();
        Platform.runLater(() -> mailBox.setOnline(online));
    }

    private boolean updateData() {
        // TODO actual update
        Gson gson = new Gson();
        try(Socket socket = new Socket("localhost", 60421)){
            try(Reader input = new InputStreamReader(socket.getInputStream())){
                if(lastUpdate != -1) return true;

                Operation op = gson.fromJson(input, Operation.class);

                mailBox.add(op.mailList());
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
