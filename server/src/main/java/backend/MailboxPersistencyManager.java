package backend;

import backend.util.LoggableError;
import com.google.gson.Gson;
import model.operationData.SimpleMail;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MailboxPersistencyManager {
    // STATIC
    private final static HashMap<String, ReadWriteLock> existingLocks = new HashMap<>();
    //FIELD
    private final ReadWriteLock fileLock;
    private final Gson gson = new Gson();
    private final File file;

    public synchronized static MailboxPersistencyManager get(String filename){
        if(filename == null)
            throw new LoggableError("Mail account owner field is null");
        filename = "mail/" + filename + ".json";

        File file = new File(filename);
        if(!file.exists())
            throw new LoggableError("One of required account doesn't exist");

        ReadWriteLock lock;
        if(existingLocks.containsKey(filename)){
            lock = existingLocks.get(filename);
        }else{
            lock = new ReentrantReadWriteLock();
            existingLocks.put(filename, lock);
        }

        return new MailboxPersistencyManager(new File(filename), lock);
    }

    private MailboxPersistencyManager(File file, ReadWriteLock lock){
        this.file = file;
        this.fileLock = lock;
    }

    public static int getNextOp(List<SimpleMail> mails){
        if(mails == null) return 0;
        return mails.isEmpty() ? 0 : mails.getLast().id() + 1;
    }

    public List<SimpleMail> readMails(){ return load(); }

    public void appendMails(List<SimpleMail> mails){
        List<SimpleMail> toWrite = load();

        int id = getNextOp(toWrite);
        for(SimpleMail mail : mails){
            toWrite.add(new SimpleMail(mail.source(), mail.destinations(), mail.object(), mail.content(),
                    id++, new Date()));
        }

        save(toWrite);
    }

    public void removeMails(List<SimpleMail> mails){
        List<SimpleMail> toWrite = load();

        boolean removed = toWrite.removeAll(mails);
        if(!removed)
            throw new LoggableError("Mail to remove not found");

        save(toWrite);
    }

    private List<SimpleMail> load(){
        List<SimpleMail> res = new ArrayList<>();

        fileLock.readLock().lock();
        try (Reader reader = new InputStreamReader(new FileInputStream(file))) {
            res.addAll(List.of(gson.fromJson(reader, SimpleMail[].class)));
        } catch (IOException e) {
            throw new LoggableError("Internal Server Error while reading");
        } finally {
            fileLock.readLock().unlock();
        }

        return res;
    }

    private void save(List<SimpleMail> toWrite){
        fileLock.writeLock().lock();
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file))) {
            gson.toJson(toWrite.toArray(), writer);
        } catch (IOException e) {
            throw new LoggableError("Internal Server Error while writing");
        } finally {
            fileLock.writeLock().unlock();
        }
    }
}
