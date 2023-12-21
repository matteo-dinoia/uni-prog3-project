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

public class FileManager { //TODO rename to MailboxPersistencyManager
    // STATIC
    private final static HashMap<String, ReadWriteLock> existingLocks = new HashMap<>();
    //FIELD
    private final ReadWriteLock fileLock;
    private final Gson gson = new Gson();
    private final File file;

    public static FileManager get(String filename){
        if(filename == null) return null;
        filename = "mail/" + filename + ".json";

        File file = new File(filename);
        if(!file.exists()) return null;

        ReadWriteLock lock;
        if(existingLocks.containsKey(filename)){
            lock = existingLocks.get(filename);
        }else{
            lock = new ReentrantReadWriteLock();
            existingLocks.put(filename, lock);
        }

        return new FileManager(new File(filename), lock);
    }

    private FileManager(File file, ReadWriteLock lock){
        this.file = file;
        this.fileLock = lock;
    }

    public static int getNextOp(List<SimpleMail> mails){
        if(mails == null) return 0;
        return mails.isEmpty() ? 0 : mails.getLast().id() + 1;
    }

    public List<SimpleMail> readMails(){ return load(); }

    public boolean appendMails(List<SimpleMail> mails){
        List<SimpleMail> toWrite = load();
        if(toWrite == null) return false;

        int id = getNextOp(toWrite);
        for(SimpleMail mail : mails){
            toWrite.add(new SimpleMail(mail.source(), mail.destinations(), mail.object(), mail.content(),
                    ++id, new Date()));
        }

        return save(toWrite);
    }

    public boolean removeMails(List<SimpleMail> mails){
        List<SimpleMail> toWrite = load();
        if(toWrite == null) return false;

        boolean removed = toWrite.removeAll(mails);
        if(!removed){
            return false;
        }

        return save(toWrite);
    }

    private List<SimpleMail> load(){
        List<SimpleMail> res = new ArrayList<>();

        fileLock.readLock().lock();
        try (Reader reader = new InputStreamReader(new FileInputStream(file))) {
            res.addAll(List.of(gson.fromJson(reader, SimpleMail[].class)));
        } catch (IOException e) {
            return null;
        } finally {
            fileLock.readLock().unlock();
        }

        return res;
    }

    private boolean save(List<SimpleMail> toWrite){
        fileLock.writeLock().lock();
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file))) {
            gson.toJson(toWrite.toArray(), writer);
        } catch (IOException e) {
            return false;
        } finally {
            fileLock.writeLock().unlock();
        }

        return true;
    }
}
