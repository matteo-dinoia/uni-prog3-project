package fontend;

import backend.Sender;
import fontend.util.StageWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Window;
import model.Mail;
import model.MailBox;

public class MainController {
    // FXML
    @FXML private ListView<Mail> receivedListView;
    @FXML private ListView<Mail> sentListView;
    @FXML private TextArea contentText;
    //Field
    private MailBox mailBox;

    public void initializeModel(MailBox mailBox){
        this.mailBox = mailBox;

        receivedListView.setItems(mailBox.getObservableListReceived());
        sentListView.setItems(mailBox.getObservableListSent());
    }

    @FXML private void updateSelectedSent(){ showMail(sentListView.getSelectionModel().getSelectedItem()); }
    @FXML private void updateSelectedReceived(){ showMail(receivedListView.getSelectionModel().getSelectedItem()); }

    private void showMail(Mail toShow){
        if(toShow == null)
            return;

        // TODO keep selected
        contentText.setText("\n" + toShow.formatted() + "\n");
    }

    @FXML private void resetSelected(){
        if(sentListView == null || receivedListView == null)
            return;

        sentListView.getSelectionModel().clearSelection();
        receivedListView.getSelectionModel().clearSelection();
        showMail(new Mail("", "", "", ""));
    }

    @FXML private void newMail(ActionEvent event){
        Window owner = ((Node)event.getSource()).getScene().getWindow();

        String id = ((Button)event.getSource()).getId();
        Mail startPoint = switch (id) {
            case "replyBtn" -> new Mail(mailBox.getOwner(), "You", "Re: ", null);
            case "replyAllBtn" -> new Mail(mailBox.getOwner(), "All", "Re: ", null);
            case "forwardBtn" -> new Mail(mailBox.getOwner(), null, "Fwd: ", null);
            default -> new Mail(mailBox.getOwner(), null, null, null);
        };

        openDialog(owner, startPoint);
    }

    private void openDialog(Window owner, Mail startPoint){
        StageWrapper stageWrapper = new StageWrapper(null, "Mail editor", 500, 400);
        stageWrapper.setModal(owner);

        MailEditorController contrEditor = stageWrapper.setRootAndGetController(getClass().getResource("mail-editor-view.fxml"));
        if (contrEditor != null){
            contrEditor.setOptionListener((Mail mail) -> { mailBox.addSent(mail); stageWrapper.close(); new Thread(new Sender(mail)).start(); });
            contrEditor.setDefaultMail(startPoint);
        }

        stageWrapper.open();
    }
}
