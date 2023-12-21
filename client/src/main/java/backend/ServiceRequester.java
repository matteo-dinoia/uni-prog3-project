package backend;

import com.google.gson.Gson;
import interfaces.EndStatusListener;
import interfaces.EndStatusNotifier;
import javafx.application.Platform;
import model.operationData.Operation;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public abstract class ServiceRequester<T> implements Runnable, EndStatusNotifier<T> {
    private EndStatusListener<T> listener;
    private final Gson gson = new Gson();

    @Override public void setOptionListener(EndStatusListener<T> listener) {
        this.listener = listener;
    }

    @Override public final void run() {
        T result = call();

        if(listener != null)
            Platform.runLater(() -> listener.useEndStatus(result));

    }

    public abstract T call();

    public Operation executeOperation(Operation op){
        try(Socket socket = new Socket("localhost", 60421)){
            try(PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)){
                try(Scanner scanner = new Scanner(socket.getInputStream())){
                    return executeOperation(op, scanner, writer);
                }
            }catch (IOException exc) {
                System.err.println("ERROR: during communication with server: " + exc.getMessage());
            }
        } catch (IOException e) {
            // Do nothing because there it is a internet problem
        }catch (Throwable throwable){
            System.err.println("ERROR: while manipulating data: " + throwable.getMessage());
        }

        return null;
    }

    private Operation executeOperation(Operation op, Scanner scanner, PrintWriter writer){
        writer.println(gson.toJson(op, Operation.class));
        return gson.fromJson(scanner.nextLine(), Operation.class);
    }
}
