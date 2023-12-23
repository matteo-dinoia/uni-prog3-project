package backend;

import interfaces.Logger;
import javafx.application.Platform;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable, Logger{
    private Logger logger;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private static Server singleInstance;
    public static Server getServer(Logger logger){
        if(singleInstance == null){
            singleInstance = new Server();
            singleInstance.logger = logger;
        }

        return singleInstance;
    }

    @Override public void run() {
        try (ServerSocket server = new ServerSocket(60421)){
            server.setSoTimeout(2000);
            Socket socket;

            logger.log("INFO", "Server initialized");
            while(!Thread.currentThread().isInterrupted()){
                try{
                    socket = server.accept();
                }catch(InterruptedIOException stopped){
                    continue;
                }

                ConnectionReplier replier = new ConnectionReplier(this, socket);
                executorService.execute(replier);
            }
        }catch (IOException exc){
            log("FATAL", "Cannot open/accept server socket");
        }

        executorService.shutdown();
    }

    @Override public void log(String type, String str) {
        Platform.runLater(() -> logger.log(type, str));
    }
}
