package fontend;

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
    private final static int WIDTH = 800;
    private final static int HEIGHT = 500;
    // Fields
    private Stage stage;
    private Parent mainContent;

    @Override public void start(Stage st) throws IOException {
        this.stage = st;

        FXMLLoader loaderLogin = new FXMLLoader(App.class.getResource("login-view.fxml"));
        stage.setScene(new Scene(loaderLogin.load(), WIDTH, HEIGHT));

        FXMLLoader loaderMain = new FXMLLoader(App.class.getResource("main-view.fxml"));
        mainContent = loaderMain.load();

        LoginController contrLogin = loaderLogin.getController();
        contrLogin.setContinueAsUser((login) -> {
            //TODO actually use username
            stage.getScene().setRoot(mainContent);
            stage.setTitle(stage.getTitle() + " - " + login);
        });

        setParameters();
    }

    private void setParameters(){
        stage.setTitle("Mail Client");
        setIcon();

        stage.setMinWidth(WIDTH);
        stage.setMinHeight(HEIGHT);
        stage.show();
    }

    private void setIcon(){
        // Icon by Icons8 at https://icons8.com/icon/xLIkjgcmFOsC/mail
        URL imageURL = App.class.getResource("icon.png");
        if(imageURL != null) stage.getIcons().add(new Image(imageURL.toExternalForm()));
        else System.err.println("Couldn't load logo");
    }

    public static void main(String[] args) { launch(); }
}
