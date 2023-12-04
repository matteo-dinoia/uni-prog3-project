package frontend;

import backend.Server;
import interfaces.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;
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
        stage.setTitle("Hello!");

        stage.setMinWidth(WIDTH);
        stage.setMinHeight(HEIGHT);
        stage.show();
    }

    private void setupServer(Logger logger){
        Server server = Server.getServer(logger);
        server.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
