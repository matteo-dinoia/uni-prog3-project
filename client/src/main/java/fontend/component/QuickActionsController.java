package fontend.component;

import backend.Sender;
import fontend.MailEditorController;
import fontend.MainController;
import fontend.util.StageWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Window;
import model.Mail;
import model.MailBox;

public class QuickActionsController {

    private MailBox mailBox = MailBox.mBoxTmp;

    @FXML
    private void newMail(ActionEvent event){
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
        stageWrapper.setIcon(MainController.class.getResource("img/icon.png"));

        MailEditorController contrEditor = stageWrapper.setRootAndGetController(MainController.class.getResource("mail-editor-view.fxml"));
        if (contrEditor != null){
            contrEditor.setOptionListener((Mail mail) -> { mailBox.add(mail); stageWrapper.close(); new Thread(new Sender(mail)).start(); });
            contrEditor.setDefaultMail(startPoint);
        }else{
            System.err.println("Coundn't load EditorController");
        }

        stageWrapper.open();
    }

    public void deleteMail() {
        //TODO
    }
}
