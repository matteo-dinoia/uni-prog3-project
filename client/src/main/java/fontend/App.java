package fontend;

import fontend.util.StageWrapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class App extends Application{
    // Constants
    private final static String TITLE = "Mail Client";
    private final static int WIDTH = 800;
    private final static int HEIGHT = 500;
    // Fields
    private StageWrapper stageWrapper;

    @Override public void start(Stage st){
        this.stageWrapper = new StageWrapper(st, TITLE, WIDTH, HEIGHT);

        loadPages();
        setParameters();
        stageWrapper.open();
    }

    private void loadPages(){
            LoginController contrLogin = stageWrapper.setRootAndGetController(getClass().getResource("login-view.fxml"));

            if(contrLogin != null) contrLogin.setContinueAsUser((login) -> {
                //TODO actually use username
                stageWrapper.setRootAndGetController(getClass().getResource("main-view.fxml"));
                stageWrapper.setTitle(TITLE + " - " + login);
            });

    }

    private void setParameters(){
        // Icon by Icons8 at https://icons8.com/icon/xLIkjgcmFOsC/mail
        stageWrapper.setIcon(App.class.getResource("icon.png"));
    }

    public static void main(String[] args) { launch(); }
}
