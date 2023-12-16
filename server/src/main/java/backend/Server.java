package backend;

import interfaces.Logger;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    private Logger logger;

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

            while(!Thread.currentThread().isInterrupted()){
                try{
                    socket = server.accept();
                }catch(InterruptedIOException stopped){
                    continue;
                }

                ConnectionReplier replier = new ConnectionReplier(logger, socket);
                new Thread(replier).start();
            }
        }catch (IOException exc){
            logger.log("FATAL: cannot open/accept server socket");
        }
    }

}
