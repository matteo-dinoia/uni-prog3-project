package backend;

import com.google.gson.Gson;
import interfaces.Logger;
import model.operationData.SimpleMail;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileManager {
    // STATIC
    private final static HashMap<String, ReadWriteLock> existingLocks = new HashMap<>();
    //FIELD
    private final ReadWriteLock lock;
    private final Gson gson = new Gson();
    private final Logger logger;
    private final File file;

    public static FileManager get(String filename, Logger logger){
        if(existingLocks.containsKey(filename))
            return new FileManager(new File(filename), existingLocks.get(filename), logger);

        ReadWriteLock lock = new ReentrantReadWriteLock();
        existingLocks.put(filename, lock);
        return new FileManager(new File(filename), lock, logger);
    }

    private FileManager(File file, ReadWriteLock lock, Logger logger){
        this.file = file;
        this.lock = lock;
        this.logger = logger;
    }

    public List<SimpleMail> readMails(){
        if (!file.exists()) {
            logger.log("ERROR: user doesn't exist");
            return null;
        }

        List<SimpleMail> res = new ArrayList<>();

        try (Reader reader = new InputStreamReader(new FileInputStream(file))) {
            res.addAll(List.of(gson.fromJson(reader, SimpleMail[].class)));
        } catch (IOException e) {
            logger.log("ERROR: couldn't read from file");
            return null;
        }

        return res;
    }

    public boolean appendMails(List<SimpleMail> mails){
        if (!file.exists()) {
            logger.log("ERROR: user doesn't exist");
            return false;
        }

        List<SimpleMail> toWrite = readMails();
        if(toWrite == null) return false;

        int id = toWrite.isEmpty() ? 0 : toWrite.getLast().id();
        for(SimpleMail mail : mails){
            toWrite.add(new SimpleMail(mail.source(), mail.destinations(), mail.object(), mail.content(),
                    ++id, new Date()));
        }

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file))) {
            gson.toJson(toWrite.toArray(), writer);
        } catch (IOException e) {
            logger.log("ERROR: couldn't write to file");
            return false;
        }

        return true;
    }

    public boolean removeMails(List<SimpleMail> mails){
        if (!file.exists()) {
            logger.log("ERROR: user doesn't exist");
            return false;
        }

        List<SimpleMail> toWrite = readMails();
        if(toWrite == null) return false;

        boolean removed = toWrite.removeAll(mails);
        if(!removed){
            logger.log("ERROR: mail to remove doesn't exist");
            return false;
        }

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file))) {
            gson.toJson(toWrite.toArray(), writer);
        } catch (IOException e) {
            logger.log("ERROR: couldn't write to file");
            return false;
        }

        return true;
    }

}
