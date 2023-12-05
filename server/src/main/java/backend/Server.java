package backend;

import interfaces.Logger;

public class Server implements Logger{
    private Logger logger;

    private static Server singleInstance;
    public static Server getServer(Logger logger){
        if(singleInstance == null){
            singleInstance = new Server();
            singleInstance.logger = logger;
        }

        return singleInstance;
    }

    public void start(){
        //TODO actual start of server
        log("Test\n");
        for(int i = 0; i < 100; i++)
            log(i + ") Testing ");
    }

    @Override public void log(String str) { if(logger != null) logger.log(str); }
}
