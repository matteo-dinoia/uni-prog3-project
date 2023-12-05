package fontend;

import fontend.util.StageWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Window;
import model.Mail;

import java.io.IOException;

public class MainController {
    // FXML
    @FXML private ListView<Mail> receivedListView;
    @FXML private ListView<Mail> sentListView;
    @FXML private TextArea contentText;
    @FXML private Button replyBtn, replyAllBtn, forwardBtn;
    // Fields
    private final ObservableList<Mail> listReceived = FXCollections.observableArrayList();
    private final ObservableList<Mail> listSent = FXCollections.observableArrayList();

    @FXML private void initialize() {
        receivedListView.setItems(listReceived);
        sentListView.setItems(listSent);
        loadMails();
    }

    private void loadMails(){
        //TODO Actual data (for now is test data)
        for(int i = 0; i < 10; i++){
            listSent.add(new Mail("Test", "you","Test Sent Mail " + i,
                    """
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Malesuada fames ac turpis egestas integer eget aliquet nibh praesent. Nec feugiat nisl pretium fusce id velit ut tortor. Eget lorem dolor sed viverra ipsum nunc. Felis donec et odio pellentesque diam volutpat commodo sed. Massa placerat duis ultricies lacus sed turpis tincidunt id aliquet. Bibendum est ultricies integer quis auctor elit sed. Blandit libero volutpat sed cras. Sed cras ornare arcu dui vivamus arcu felis bibendum. Curabitur gravida arcu ac tortor dignissim convallis aenean. Diam quam nulla porttitor massa. Morbi leo urna molestie at elementum. Dolor purus non enim praesent elementum. Orci nulla pellentesque dignissim enim sit amet venenatis. Velit laoreet id donec ultrices tincidunt arcu non sodales. Sagittis eu volutpat odio facilisis mauris sit amet massa vitae. Leo duis ut diam quam nulla porttitor.

                            Tellus orci ac auctor augue mauris augue neque. Viverra vitae congue eu consequat ac felis donec et. Tempus iaculis urna id volutpat lacus laoreet non. Malesuada nunc vel risus commodo. Porta non pulvinar neque laoreet suspendisse interdum consectetur libero. Faucibus interdum posuere lorem ipsum dolor sit. Id interdum velit laoreet id. Diam quis enim lobortis scelerisque. Sem fringilla ut morbi tincidunt augue interdum. In vitae turpis massa sed. Cras sed felis eget velit aliquet sagittis. Non diam phasellus vestibulum lorem sed risus ultricies tristique. Eget magna fermentum iaculis eu non diam. Ullamcorper eget nulla facilisi etiam dignissim. Sit amet nisl purus in mollis nunc sed id semper.

                            Nisl nisi scelerisque eu ultrices vitae auctor. Eget est lorem ipsum dolor sit. Condimentum id venenatis a condimentum vitae sapien pellentesque habitant. In eu mi bibendum neque egestas congue. Elementum eu facilisis sed odio. Et pharetra pharetra massa massa ultricies. Felis eget velit aliquet sagittis id consectetur purus ut faucibus. Lectus magna fringilla urna porttitor rhoncus dolor purus non. Mi bibendum neque egestas congue quisque. Odio aenean sed adipiscing diam donec adipiscing tristique risus. Nunc sed blandit libero volutpat sed. Cursus turpis massa tincidunt dui ut ornare lectus sit amet. Consequat interdum varius sit amet mattis vulputate enim nulla aliquet. Faucibus purus in massa tempor nec feugiat nisl. Lectus arcu bibendum at varius. Fringilla ut morbi tincidunt augue interdum. Arcu odio ut sem nulla pharetra. Porttitor massa id neque aliquam vestibulum. Eget dolor morbi non arcu. Aliquet eget sit amet tellus cras adipiscing enim.

                            Arcu cursus vitae congue mauris rhoncus aenean. Rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar. In iaculis nunc sed augue lacus viverra vitae congue eu. Nisl rhoncus mattis rhoncus urna neque viverra. Tincidunt id aliquet risus feugiat in. Enim sit amet venenatis urna cursus eget nunc scelerisque viverra. Quam vulputate dignissim suspendisse in est ante. Vitae auctor eu augue ut. Nulla facilisi nullam vehicula ipsum. Amet cursus sit amet dictum sit amet justo. Vel fringilla est ullamcorper eget nulla.

                            Amet tellus cras adipiscing enim eu turpis egestas pretium aenean. Diam sit amet nisl suscipit. Praesent elementum facilisis leo vel fringilla. Posuere sollicitudin aliquam ultrices sagittis orci a scelerisque purus. Et netus et malesuada fames ac turpis egestas. Vulputate odio ut enim blandit volutpat maecenas volutpat blandit aliquam. Volutpat maecenas volutpat blandit aliquam etiam erat velit scelerisque. Sodales ut etiam sit amet nisl purus in mollis nunc. Magna fermentum iaculis eu non diam phasellus vestibulum. Vulputate odio ut enim blandit volutpat maecenas volutpat blandit. Enim blandit volutpat maecenas volutpat blandit aliquam. Sagittis vitae et leo duis ut diam."""));
            listReceived.add(new Mail("Test", "you", "Test Received Mail " + i,
                    """
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Malesuada fames ac turpis egestas integer eget aliquet nibh praesent. Nec feugiat nisl pretium fusce id velit ut tortor. Eget lorem dolor sed viverra ipsum nunc. Felis donec et odio pellentesque diam volutpat commodo sed. Massa placerat duis ultricies lacus sed turpis tincidunt id aliquet. Bibendum est ultricies integer quis auctor elit sed. Blandit libero volutpat sed cras. Sed cras ornare arcu dui vivamus arcu felis bibendum. Curabitur gravida arcu ac tortor dignissim convallis aenean. Diam quam nulla porttitor massa. Morbi leo urna molestie at elementum. Dolor purus non enim praesent elementum. Orci nulla pellentesque dignissim enim sit amet venenatis. Velit laoreet id donec ultrices tincidunt arcu non sodales. Sagittis eu volutpat odio facilisis mauris sit amet massa vitae. Leo duis ut diam quam nulla porttitor.

                            Tellus orci ac auctor augue mauris augue neque. Viverra vitae congue eu consequat ac felis donec et. Tempus iaculis urna id volutpat lacus laoreet non. Malesuada nunc vel risus commodo. Porta non pulvinar neque laoreet suspendisse interdum consectetur libero. Faucibus interdum posuere lorem ipsum dolor sit. Id interdum velit laoreet id. Diam quis enim lobortis scelerisque. Sem fringilla ut morbi tincidunt augue interdum. In vitae turpis massa sed. Cras sed felis eget velit aliquet sagittis. Non diam phasellus vestibulum lorem sed risus ultricies tristique. Eget magna fermentum iaculis eu non diam. Ullamcorper eget nulla facilisi etiam dignissim. Sit amet nisl purus in mollis nunc sed id semper.

                            Nisl nisi scelerisque eu ultrices vitae auctor. Eget est lorem ipsum dolor sit. Condimentum id venenatis a condimentum vitae sapien pellentesque habitant. In eu mi bibendum neque egestas congue. Elementum eu facilisis sed odio. Et pharetra pharetra massa massa ultricies. Felis eget velit aliquet sagittis id consectetur purus ut faucibus. Lectus magna fringilla urna porttitor rhoncus dolor purus non. Mi bibendum neque egestas congue quisque. Odio aenean sed adipiscing diam donec adipiscing tristique risus. Nunc sed blandit libero volutpat sed. Cursus turpis massa tincidunt dui ut ornare lectus sit amet. Consequat interdum varius sit amet mattis vulputate enim nulla aliquet. Faucibus purus in massa tempor nec feugiat nisl. Lectus arcu bibendum at varius. Fringilla ut morbi tincidunt augue interdum. Arcu odio ut sem nulla pharetra. Porttitor massa id neque aliquam vestibulum. Eget dolor morbi non arcu. Aliquet eget sit amet tellus cras adipiscing enim.

                            Arcu cursus vitae congue mauris rhoncus aenean. Rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar. In iaculis nunc sed augue lacus viverra vitae congue eu. Nisl rhoncus mattis rhoncus urna neque viverra. Tincidunt id aliquet risus feugiat in. Enim sit amet venenatis urna cursus eget nunc scelerisque viverra. Quam vulputate dignissim suspendisse in est ante. Vitae auctor eu augue ut. Nulla facilisi nullam vehicula ipsum. Amet cursus sit amet dictum sit amet justo. Vel fringilla est ullamcorper eget nulla.

                            Amet tellus cras adipiscing enim eu turpis egestas pretium aenean. Diam sit amet nisl suscipit. Praesent elementum facilisis leo vel fringilla. Posuere sollicitudin aliquam ultrices sagittis orci a scelerisque purus. Et netus et malesuada fames ac turpis egestas. Vulputate odio ut enim blandit volutpat maecenas volutpat blandit aliquam. Volutpat maecenas volutpat blandit aliquam etiam erat velit scelerisque. Sodales ut etiam sit amet nisl purus in mollis nunc. Magna fermentum iaculis eu non diam phasellus vestibulum. Vulputate odio ut enim blandit volutpat maecenas volutpat blandit. Enim blandit volutpat maecenas volutpat blandit aliquam. Sagittis vitae et leo duis ut diam."""));
        }
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

        // TODO use acutal sender,...
        if(event.getSource() == replyBtn)
            openDialog(owner, new Mail("ME", "You", "Re: ", null));
        else if(event.getSource() == replyAllBtn)
            openDialog(owner, new Mail("ME", "All", "Re: ", null));
        else if(event.getSource() == forwardBtn)
            openDialog(owner, new Mail("ME", null, "Fwd: ", null));
        else
            openDialog(owner, new Mail("ME", null, null, null));
    }

    private void openDialog(Window owner, Mail startPoint){
        StageWrapper stageWrapper = new StageWrapper(null, "Mail editor", 500, 400);
        stageWrapper.setModal(owner);

        MailEditorController contrEditor = stageWrapper.setRootAndGetController(getClass().getResource("mail-editor-view.fxml"));
        if (contrEditor != null){
            contrEditor.setOptionListener((Mail mail) -> { listSent.add(mail); stageWrapper.close(); });
            contrEditor.setDefaultMail(startPoint);
        }

        stageWrapper.open();
    }
}
