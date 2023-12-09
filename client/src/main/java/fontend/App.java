package fontend;

import backend.Updater;
import fontend.util.StageWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import model.MailAddress;
import model.MailBox;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class App extends Application{
    // Constants
    private final static String TITLE = "Mail Client";
    private final static int WIDTH = 800;
    private final static int HEIGHT = 500;
    private final static int TIME_TO_UPDATE = 10;
    // Fields
    private StageWrapper stageWrapper;
    private final ScheduledExecutorService scheduler = newScheduledThreadPool(1);

    @Override public void start(Stage st){
        this.stageWrapper = new StageWrapper(st, TITLE, WIDTH, HEIGHT);

        loadPages();
        setParameters();
        stageWrapper.open();
    }

    public void startUpdater(MailBox mailBox){
        scheduler.scheduleAtFixedRate(new Updater(mailBox), 0, TIME_TO_UPDATE, TimeUnit.SECONDS);
    }

    private void loadPages(){
        LoginController contrLogin = stageWrapper.setRootAndGetController(getClass().getResource("login-view.fxml"));

        if(contrLogin != null)
            contrLogin.setContinueAsUser(this::loadMainPage);
    }

    private void loadMainPage(String login){
        MailBox mailBox = new MailBox(new MailAddress(login));

        MainController contrEditor = stageWrapper.setRootAndGetController(getClass().getResource("main-view.fxml"));
        if(contrEditor != null)
            contrEditor.initializeModel(mailBox);

        stageWrapper.setTitle(TITLE + " - " + login);
        startUpdater(mailBox);
    }

    private void setParameters(){
        // Icon by Icons8 at https://icons8.com/icon/xLIkjgcmFOsC/mail
        stageWrapper.setIcon(App.class.getResource("icon.png"));
        stageWrapper.setOnClose((event) -> scheduler.shutdownNow());
    }

    public static void main(String[] args) { launch(); }
}
