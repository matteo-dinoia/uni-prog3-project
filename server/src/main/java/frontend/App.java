package frontend;

import backend.Server;
import interfaces.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class App extends Application {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 500;
    private Stage stage;

    @Override
    public void start(Stage st) throws IOException {
        this.stage = st;

        FXMLLoader loader = new FXMLLoader(App.class.getResource("log-view.fxml"));
        stage.setScene(new Scene(loader.load(), WIDTH, HEIGHT));

        setupServer(loader.getController());
        setParameters();
    }

    private void setParameters(){
        stage.setTitle("Mail Server");

        stage.setMinWidth(WIDTH);
        stage.setMinHeight(HEIGHT);
        stage.show();

        URL imageURL = getClass().getResource("img/icon.png");
        if(imageURL != null)
            stage.getIcons().add(new Image(imageURL.toExternalForm()));
        else
            System.err.println("Couldn't load logo");
    }

    private void setupServer(Logger logger){
        Server server = Server.getServer(logger);
        Thread threadServer = new Thread(server);
        threadServer.start();

        stage.setOnCloseRequest((event) -> threadServer.interrupt());
    }

    public static void main(String[] args) {
        launch();
    }
}
