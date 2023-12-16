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
    private MailBox mailBox;

    public void initializeModel(MailBox mailBox){
        this.mailBox = mailBox;

        receivedListView.setItems(mailBox.getObservableListReceived());
        sentListView.setItems(mailBox.getObservableListSent());
    }

    @FXML private void updateSelectedSent(){ updateSelected(sentListView.getSelectionModel().getSelectedItem()); }
    @FXML private void updateSelectedReceived(){ updateSelected(receivedListView.getSelectionModel().getSelectedItem()); }

    private void updateSelected(Mail mail){
        if(mail == null) return;

        mailBox.selectedMail.getFromProperty().bind(mail.getFromProperty());
        mailBox.selectedMail.getToProperty().bind(mail.getToProperty());
        mailBox.selectedMail.getObjectProperty().bind(mail.getObjectProperty());
        mailBox.selectedMail.getContentProperty().bind(mail.getContentProperty());
    }

    @FXML private void resetSelected(){
        if(sentListView == null || receivedListView == null)
            return; // because it is called when not finished initializing

        sentListView.getSelectionModel().clearSelection();
        receivedListView.getSelectionModel().clearSelection();
        /*showMail(null);*/
    }


}
