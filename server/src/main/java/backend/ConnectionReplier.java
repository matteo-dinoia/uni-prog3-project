package backend;

import interfaces.Logger;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import model.Mail;

public class ConnectionReplier implements Runnable{
    private final Logger logger;
    private Socket socket;

    public ConnectionReplier(Logger logger, Socket socket) {
        this.socket = socket;
        this.logger = logger;
    }


    @Override public void run() {
        Gson gson = new Gson();

        try(Writer output = new OutputStreamWriter(socket.getOutputStream());){
            logger.log("START COMMUNITCATION WITH CLIENT");
            String s = gson.toJson(testMails());
            logger.log("RESPONDING (num char: " + s.length() + ")");
            output.write(s);
        }catch (IOException exc){
            logger.log("ERROR: during communication with client");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.log("ERROR: could not close socket");
            }
        }
        logger.log("ENDED");
    }

    private Mail[] testMails(){
        Mail res[] = new Mail[20];
        //TODO Actual data (for now is test data)
        String textTest = """
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Malesuada fames ac turpis egestas integer eget aliquet nibh praesent. Nec feugiat nisl pretium fusce id velit ut tortor. Eget lorem dolor sed viverra ipsum nunc. Felis donec et odio pellentesque diam volutpat commodo sed. Massa placerat duis ultricies lacus sed turpis tincidunt id aliquet. Bibendum est ultricies integer quis auctor elit sed. Blandit libero volutpat sed cras. Sed cras ornare arcu dui vivamus arcu felis bibendum. Curabitur gravida arcu ac tortor dignissim convallis aenean. Diam quam nulla porttitor massa. Morbi leo urna molestie at elementum. Dolor purus non enim praesent elementum. Orci nulla pellentesque dignissim enim sit amet venenatis. Velit laoreet id donec ultrices tincidunt arcu non sodales. Sagittis eu volutpat odio facilisis mauris sit amet massa vitae. Leo duis ut diam quam nulla porttitor.
                            Tellus orci ac auctor augue mauris augue neque. Viverra vitae congue eu consequat ac felis donec et. Tempus iaculis urna id volutpat lacus laoreet non. Malesuada nunc vel risus commodo. Porta non pulvinar neque laoreet suspendisse interdum consectetur libero. Faucibus interdum posuere lorem ipsum dolor sit. Id interdum velit laoreet id. Diam quis enim lobortis scelerisque. Sem fringilla ut morbi tincidunt augue interdum. In vitae turpis massa sed. Cras sed felis eget velit aliquet sagittis. Non diam phasellus vestibulum lorem sed risus ultricies tristique. Eget magna fermentum iaculis eu non diam. Ullamcorper eget nulla facilisi etiam dignissim. Sit amet nisl purus in mollis nunc sed id semper.
                            Nisl nisi scelerisque eu ultrices vitae auctor. Eget est lorem ipsum dolor sit. Condimentum id venenatis a condimentum vitae sapien pellentesque habitant. In eu mi bibendum neque egestas congue. Elementum eu facilisis sed odio. Et pharetra pharetra massa massa ultricies. Felis eget velit aliquet sagittis id consectetur purus ut faucibus. Lectus magna fringilla urna porttitor rhoncus dolor purus non. Mi bibendum neque egestas congue quisque. Odio aenean sed adipiscing diam donec adipiscing tristique risus. Nunc sed blandit libero volutpat sed. Cursus turpis massa tincidunt dui ut ornare lectus sit amet. Consequat interdum varius sit amet mattis vulputate enim nulla aliquet. Faucibus purus in massa tempor nec feugiat nisl. Lectus arcu bibendum at varius. Fringilla ut morbi tincidunt augue interdum. Arcu odio ut sem nulla pharetra. Porttitor massa id neque aliquam vestibulum. Eget dolor morbi non arcu. Aliquet eget sit amet tellus cras adipiscing enim.
                            Arcu cursus vitae congue mauris rhoncus aenean. Rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar. In iaculis nunc sed augue lacus viverra vitae congue eu. Nisl rhoncus mattis rhoncus urna neque viverra. Tincidunt id aliquet risus feugiat in. Enim sit amet venenatis urna cursus eget nunc scelerisque viverra. Quam vulputate dignissim suspendisse in est ante. Vitae auctor eu augue ut. Nulla facilisi nullam vehicula ipsum. Amet cursus sit amet dictum sit amet justo. Vel fringilla est ullamcorper eget nulla.
                            Amet tellus cras adipiscing enim eu turpis egestas pretium aenean. Diam sit amet nisl suscipit. Praesent elementum facilisis leo vel fringilla. Posuere sollicitudin aliquam ultrices sagittis orci a scelerisque purus. Et netus et malesuada fames ac turpis egestas. Vulputate odio ut enim blandit volutpat maecenas volutpat blandit aliquam. Volutpat maecenas volutpat blandit aliquam etiam erat velit scelerisque. Sodales ut etiam sit amet nisl purus in mollis nunc. Magna fermentum iaculis eu non diam phasellus vestibulum. Vulputate odio ut enim blandit volutpat maecenas volutpat blandit. Enim blandit volutpat maecenas volutpat blandit aliquam. Sagittis vitae et leo duis ut diam.""";

        for(int i = 0; i < 10; i++){
            res[i + 10] = new Mail("testuser@gmail.com", "other","Test Sent Mail " + i, textTest);
            res[i] = new Mail("other", "testuser@gmail.com", "Test Received Mail " + i, textTest);
        }

        return res;
    }
}
