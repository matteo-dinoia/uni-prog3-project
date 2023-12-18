package frontend.component;

import backend.Sender;
import frontend.MailEditorController;
import frontend.MainController;
import frontend.util.StageWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Window;
import model.Mail;
import model.MailBox;

public class QuickActionsController {
    // FXML
    @FXML private Button replyBtn, replyAllBtn, forwardBtn, deleteBtn;
    // Field
    private final MailBox mailBox = MailBox.mBoxTmp; // TODO change

    @FXML private void initialize(){
        replyBtn.disableProperty().bind(mailBox.selectionExistProperty().not());
        replyAllBtn.disableProperty().bind(mailBox.selectionExistProperty().not());
        forwardBtn.disableProperty().bind(mailBox.selectionExistProperty().not());
        deleteBtn.disableProperty().bind(mailBox.selectionExistProperty().not());
    }

    @FXML private void openMailEditor(ActionEvent event){
        Window owner = ((Node)event.getSource()).getScene().getWindow();

        String id = ((Button)event.getSource()).getId();
        Mail startPoint = switch (id) {
            case "replyBtn" -> new Mail(mailBox.getOwner(), "You", "Re: ", null);
            case "replyAllBtn" -> new Mail(mailBox.getOwner(), "All", "Re: ", null);
            case "forwardBtn" -> new Mail(mailBox.getOwner(), null, "Fwd: ", null);
            case "deleteBtn" -> new Mail(mailBox.getSelectedMail());
            default -> new Mail(mailBox.getOwner(), null, null, null);
        };

        openDialog(owner, startPoint, deleteBtn.getId().equals(id));
    }

    private void openDialog(Window owner, Mail startPoint, boolean isDeletion){
        StageWrapper stageWrapper = new StageWrapper(null, "Mail editor", 500, 400);
        stageWrapper.setModal(owner);
        stageWrapper.setIcon(MainController.class.getResource("img/icon.png"));

        MailEditorController contrEditor = stageWrapper.setRootAndGetController(MainController.class.getResource("mail-editor-view.fxml"));
        if (contrEditor != null){
            contrEditor.initializeModel(mailBox);
            contrEditor.setMail(startPoint, isDeletion);
            contrEditor.setOptionListener((Mail mail) -> {
                if(isDeletion){
                    // TODO add remover
                    //mailBox.remove(mail); TODO fix doesn't work
                }else{
                    mailBox.add(mail); // TODO Remove
                    new Thread(new Sender(mail)).start();
                }

                stageWrapper.close();
            });
        }else{
            System.err.println("Coundn't load EditorController");
        }

        stageWrapper.open();
    }
}
