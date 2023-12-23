package frontend;

import backend.Updater;
import frontend.util.StageWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import model.MailBox;
import model.operationData.SimpleMail;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;

public class App extends Application{
    // Static
    public static MailBox singleMailBox;
    // Constants
    private final static String TITLE = "Mail Client";
    private final static int WIDTH = 800;
    private final static int HEIGHT = 500;
    private final static int TIME_TO_UPDATE = 5;
    // Fields
    private StageWrapper stageWrapper;
    private StageWrapper dialog;
    private boolean firstTime = true;
    private int numUnread = 0;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override public void start(Stage st){
        this.stageWrapper = new StageWrapper(st, TITLE, WIDTH, HEIGHT);

        loadPages();
        setParameters();
        stageWrapper.open();
    }

    private void loadPages(){
        LoginController contrLogin = stageWrapper.setRootAndGetController(getClass().getResource("login-view.fxml"));

        if(contrLogin != null)
            contrLogin.setContinueAsUser(this::loadMainPage);
    }

    private void loadMainPage(String login){
        App.singleMailBox = new MailBox(login);

        stageWrapper.setRootAndGetController(getClass().getResource("main-view.fxml"));
        stageWrapper.setTitle(TITLE + " - " + login);

        Updater updater = new Updater();
        updater.setOptionListener(this::notifyNewMail);
        scheduler.scheduleAtFixedRate(updater, 0, TIME_TO_UPDATE, TimeUnit.SECONDS);
    }

    private void notifyNewMail(List<SimpleMail> newMails) {
        boolean online = newMails != null;
        singleMailBox.setOnline(online);
        if(!online) return;

        singleMailBox.add(newMails);
        if(firstTime){
            firstTime = false;
            return;
        }

        int changed = newMails.stream().filter((mail) ->
                !mail.source().equals(singleMailBox.getOwner()))
                .toList().size();
        openDialog(changed);
    }

    private void openDialog(int changed){
        if(changed <= 0)
            return;

        numUnread += changed;
        if(dialog == null){
            dialog = StageWrapper.getMessageDialog(stageWrapper.getOwner(),
                    numUnread + " New Message/s - " + singleMailBox.getOwner(),
                    "There are unread message/s",  300, 50);
            dialog.setIcon(getClass().getResource("img/icon.png"));
            dialog.setOnClose((event) -> numUnread = 0);
            dialog.open();
        }else{
            dialog.setTitle(numUnread + " New Message/s - " + singleMailBox.getOwner());
            dialog.open();
        }
    }

    private void setParameters(){
        // Icon by Icons8 at https://icons8.com/icon/xLIkjgcmFOsC/mail
        stageWrapper.setIcon(getClass().getResource("img/icon.png"));
        stageWrapper.setOnClose((event) -> scheduler.shutdownNow());
    }

    public static void main(String[] args) { launch(); }
}
