package fontend;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Mail;
import model.MailBox;

public class MainController {
    // FXML
    @FXML private ListView<Mail> receivedListView;
    @FXML private ListView<Mail> sentListView;
    //Field
    private MailBox mailBox;

    public void initializeModel(MailBox mailBox){
        this.mailBox = mailBox;

        setOnline(false);
        mailBox.getOnlineProperty().addListener((observable, old, value) -> setOnline(value));

        receivedListView.setItems(mailBox.getObservableListReceived());
        sentListView.setItems(mailBox.getObservableListSent());
    }

    @FXML private void updateSelectedSent(){ showMail(sentListView.getSelectionModel().getSelectedItem()); }
    @FXML private void updateSelectedReceived(){ showMail(receivedListView.getSelectionModel().getSelectedItem()); }



    @FXML private void resetSelected(){
        if(sentListView == null || receivedListView == null)
            return; // because it is called when not finished initializing

        sentListView.getSelectionModel().clearSelection();
        receivedListView.getSelectionModel().clearSelection();
        showMail(null);
    }

    private void setOnline(boolean online){
        /*
        newBtn.setDisable(!online);
        replyBtn.setDisable(!online);
        replyAllBtn.setDisable(!online);
        forwardBtn.setDisable(!online);
        deleteBtn.setDisable(!online);*/
    }
}
