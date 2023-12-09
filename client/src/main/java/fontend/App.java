package fontend;

import backend.Updater;
import fontend.util.StageWrapper;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class App extends Application{
    // Constants
    private final static String TITLE = "Mail Client";
    private final static int WIDTH = 800;
    private final static int HEIGHT = 500;
    private final static int TIME_TO_UPDATE = 2;
    // Fields
    private StageWrapper stageWrapper;
    private ScheduledExecutorService scheduler = newScheduledThreadPool(1);

    @Override public void start(Stage st){
        this.stageWrapper = new StageWrapper(st, TITLE, WIDTH, HEIGHT);

        loadPages();
        setParameters();
        stageWrapper.open();
    }

    public void startUpdater(){

        scheduler.scheduleAtFixedRate(new Updater(), 0, TIME_TO_UPDATE, TimeUnit.SECONDS);
    }

    private void loadPages(){
            LoginController contrLogin = stageWrapper.setRootAndGetController(getClass().getResource("login-view.fxml"));

            if(contrLogin != null) contrLogin.setContinueAsUser((login) -> {
                //TODO actually use username
                stageWrapper.setRootAndGetController(getClass().getResource("main-view.fxml"));
                stageWrapper.setTitle(TITLE + " - " + login);
                startUpdater();
            });
    }

    private void setParameters(){
        // Icon by Icons8 at https://icons8.com/icon/xLIkjgcmFOsC/mail
        stageWrapper.setIcon(App.class.getResource("icon.png"));
        stageWrapper.setOnClose((event) -> scheduler.shutdownNow());
    }

    public static void main(String[] args) { launch(); }
}
