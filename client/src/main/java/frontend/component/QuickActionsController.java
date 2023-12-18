package frontend.component;

import backend.Sender;
import frontend.MailEditorController;
import frontend.MainController;
import frontend.util.StageWrapper;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Window;
import model.Mail;
import model.MailBox;

public class QuickActionsController {
    // FXML
    @FXML private Button replyBtn, replyAllBtn, forwardBtn, deleteBtn;
    // Field
    private final MailBox mailBox = MailBox.mBoxTmp; // TODO change

    @FXML private void initialize(){
        BooleanBinding emptySelectionProperty = mailBox.selectionExistProperty().not();
        forwardBtn.disableProperty().bind(emptySelectionProperty);
        deleteBtn.disableProperty().bind(emptySelectionProperty);

        BooleanBinding invalidSelectionProperty = mailBox.getSelectedMail().fromProperty().isEqualTo(mailBox.getOwner());
        replyBtn.disableProperty().bind(emptySelectionProperty.or(invalidSelectionProperty));
        replyAllBtn.disableProperty().bind(emptySelectionProperty.or(invalidSelectionProperty));

    }

    @FXML private void openMailEditor(ActionEvent event){
        Window owner = ((Node)event.getSource()).getScene().getWindow();

        String id = ((Button)event.getSource()).getId();
        Mail selected = mailBox.getSelectedMail();
        // TODO clean code
        Mail startPoint = switch (id) {
            case "replyBtn" -> new Mail(mailBox.getOwner(), selected.getFrom(),
                    "Re: ", "Replying to: \n | " + selected.formatted().replace("\n", "\n | "));
            case "replyAllBtn" -> new Mail(mailBox.getOwner(),
                    (selected.getFrom() + ", " + selected.getTo()).replace(mailBox.getOwner() + ", ", "").replace(", " + mailBox.getOwner(), ""),
                    "Re: ", "Replying to: \n | " + selected.formatted().replace("\n", "\n | "));
            case "forwardBtn" -> new Mail(mailBox.getOwner(), null,
                    "Fwd: " + selected.getObject(), "Forwarding to: \n | " + selected.formatted().replace("\n", "\n | "));
            case "deleteBtn" -> new Mail(selected);
            case null, default -> new Mail(mailBox.getOwner(), null, null, null);
        };

        openDialog(owner, startPoint, deleteBtn.getId().equals(id));
    }

    private void openDialog(Window owner, Mail startPoint, boolean isDeletion){
        StageWrapper stageWrapper = new StageWrapper(null, "Mail editor", 650, 450);
        stageWrapper.setModal(owner);
        stageWrapper.setIcon(MainController.class.getResource("img/icon.png"));

        MailEditorController contrEditor = stageWrapper.setRootAndGetController(MainController.class.getResource("mail-editor-view.fxml"));
        if (contrEditor != null){
            contrEditor.initializeModel(mailBox);
            contrEditor.setMail(startPoint, isDeletion);
            contrEditor.setOptionListener((Mail mail) -> {
                if(isDeletion){
                    // TODO add remover
                    mailBox.remove(mail); //TODO remove
                }else{
                    mailBox.add(mail); // TODO remove
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
