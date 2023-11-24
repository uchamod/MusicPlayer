
package musicplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MusicPlayer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("RODENTS.MP3 (Black & White Mode)");
        Image icon=new Image("rat3.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
        
    }

   
    public static void main(String[] args) {
        launch(args);
    }
    
}
