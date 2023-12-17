package frontend.util;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.net.URL;

public class StageWrapper {
    private Stage stage;

    // Set null to create new stage
    public StageWrapper(Stage st, String title, int minWidth, int minHeight){
        if((this.stage = st) == null)
            this.stage = new Stage();

        stage.setTitle(title);
        stage.setMinWidth(minWidth);
        stage.setMinHeight(minHeight);
    }

    public void setTitle(String title){ stage.setTitle(title); }

    public void setIcon(URL imageURL){
        if(imageURL != null)
            stage.getIcons().add(new Image(imageURL.toExternalForm()));
        else
            System.err.println("Couldn't load logo");
    }

    public void open(){ stage.show(); }
    public void close(){ stage.close(); }

    public void setRoot(Parent element) {
        Scene scene = stage.getScene();

        if(scene != null)
            scene.setRoot(element);
        else
            stage.setScene(new Scene(element));
    }

    public <T> T setRootAndGetController(URL url) {
        if(url == null)
            return null;

        try{
            FXMLLoader loader = new FXMLLoader(url);
            setRoot(loader.load());
            return loader.getController();
        }catch (IOException e){
            System.err.println("ERROR: couldn't load root page: " + url.toExternalForm());
            System.err.println(e.getMessage());
        }

        return null;
    }

    public void setModal(Window window) {
        if(window == null){
            stage.initModality(Modality.NONE);
            return;
        }

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(window);
    }

    public void setOnClose(EventHandler<WindowEvent> handler) {
        stage.setOnCloseRequest(handler);
    }
}
