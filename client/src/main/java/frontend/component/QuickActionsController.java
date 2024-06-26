package frontend.component;

import backend.Deleter;
import backend.Sender;
import backend.ServiceRequester;
import frontend.App;
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
    private final MailBox mailBox =  App.singleMailBox;

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

        Mail startPoint = switch (id) {
            case "replyBtn" -> new Mail(mailBox.getOwner(), selected.getFrom(), "Re: "  + selected.getObject(),
                    "Replying to:\n" + selected.formattedReference());
            case "replyAllBtn" -> new Mail(mailBox.getOwner(), // TODO can be better
                    (selected.getFrom() + ", " + selected.getTo()).replace(mailBox.getOwner() + ", ", "").replace(", " + mailBox.getOwner(), ""),
                    "Re: " + selected.getObject(),
                    "Replying to:\n" + selected.formattedReference());
            case "forwardBtn" -> new Mail(mailBox.getOwner(), null, "Fwd: " + selected.getObject(), "Forwarding to:\n" + selected.formattedReference());
            case "deleteBtn" -> new Mail(selected);
            case null, default -> new Mail(mailBox.getOwner(), null, null, null);
        };

        openDialog(owner, startPoint, deleteBtn.getId().equals(id));
    }

    private void openDialog(Window owner, Mail startPoint, boolean isDeletion){
        StageWrapper stageWrapper = new StageWrapper(null, "Mail editor - " + mailBox.getOwner(), 650, 450);
        stageWrapper.setModal(owner);
        stageWrapper.setIcon(MainController.class.getResource("img/icon.png"));

        MailEditorController contrEditor = stageWrapper.setRootAndGetController(MainController.class.getResource("mail-editor-view.fxml"));
        if (contrEditor != null){
            contrEditor.setMail(startPoint, isDeletion);
            contrEditor.setOptionListener(mail -> handleMailFromEditor(mail, isDeletion, stageWrapper, contrEditor));
        }else{
            System.err.println("Coundn't load EditorController");
        }

        stageWrapper.open();
    }

    private void handleMailFromEditor(Mail mail, boolean isDeletion, StageWrapper stage, MailEditorController controller){
        if(mail == null){
            stage.close();
            return;
        }

        ServiceRequester<String> service;
        if(isDeletion) service = new Deleter(mailBox.getOwner(), mail);
        else service = new Sender(mailBox.getOwner(), mail);

        service.setOptionListener(errorStr ->{
            if (errorStr != null) {
                controller.setError(errorStr);
                return;
            }

            if(isDeletion) mailBox.remove(mail);
            stage.close();
        });
        new Thread(service).start();
    }
}
