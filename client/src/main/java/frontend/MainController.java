package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Mail;
import model.MailBox;

public class MainController {
    // FXML
    @FXML private ListView<Mail> receivedListView;
    @FXML private ListView<Mail> sentListView;
    //Field
    private final MailBox mailBox = App.singleMailBox;

    @FXML private void initialize(){
        receivedListView.setItems(mailBox.observableListReceived());
        sentListView.setItems(mailBox.observableListSent());
    }

    @FXML private void updateSelectedSent(){ mailBox.setSelectedMail(sentListView.getSelectionModel().getSelectedItem()); }
    @FXML private void updateSelectedReceived(){ mailBox.setSelectedMail(receivedListView.getSelectionModel().getSelectedItem()); }

    @FXML private void resetSelected(){
        if(sentListView == null || receivedListView == null)
            return; // because it is called when not finished initializing

        sentListView.getSelectionModel().clearSelection();
        receivedListView.getSelectionModel().clearSelection();
        mailBox.setSelectedMail(null);
    }


}
