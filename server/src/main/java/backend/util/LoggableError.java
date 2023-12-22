package backend.util;

public class LoggableError extends Error{
    public LoggableError(String msg){
        super(msg);
    }
}
