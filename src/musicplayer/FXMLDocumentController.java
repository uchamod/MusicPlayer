package musicplayer;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.util.Duration;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Button play, resst, prvious, next;
    @FXML
    private Label lbl, endtime;

    @FXML
    private ProgressBar lenth;
    @FXML
    private Slider volume;

    @FXML
    private ComboBox<String> speed;
    @FXML
    private ImageView imageloop,music,vol;
    private File derect;
    private File[] files;
    private ArrayList<File> songs;

    private boolean playing;
    private int songnumb;
    private Timer time,imagetime;
    private TimerTask task,imagetask;

    private Media media;
    private MediaPlayer mediaplayer;

    private int speeds[] = {25, 50, 75, 100, 125, 150, 175, 200};
    private boolean control = true;
   
    private Image pic,vloumeimage,soundimage;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagetime=new Timer();
        imagetask=new TimerTask() {
            @Override
            public void run() {
                for(int i=1;i<=10;i++){
                   pic=new Image(Integer.toString(i)+".png");
                   imageloop.setImage(pic);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        imagetime.scheduleAtFixedRate(imagetask, 0, 500);
      

        derect = new File("songfile"); //get the file directry 
        files = derect.listFiles();    //pass the file objects(songs) to the file arry
        songs = new ArrayList<>();    //initialize the arrylist
        if (files != null) {
            for (File song : files) {   //pass the songs to song file objet and set one by one to arry list
                songs.add(song);
               
            }
        }
        media = new Media(songs.get(songnumb).toURI().toString()); //set the songnumberindex
        mediaplayer = new MediaPlayer(media);  //pass it to mediaplayer

         //set the label name as song name

        for (int i = 0; i < speeds.length; i++) {       //add speedtypes to combo box
            speed.getItems().add(Integer.toString(speeds[i]));
        }

        speed.setOnAction(this::speedup);

        volume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
               vloumeimage = new Image("mute.png");
               soundimage=new Image("vol.png");
               mediaplayer.setVolume(volume.getValue() * 0.01);
               if(volume.getValue()==0){
                   vol.setImage(vloumeimage);
               }else{
                   vol.setImage(soundimage);
               }
                
            }

        });
        
        
    }

    public void playit() {
        
        
        if (control == true) {
            control = false;
            btime();

            speedup(null);
            mediaplayer.setVolume(volume.getValue() * 0.01);
            String songname=songs.get(songnumb).getName();
            String songsize=songname.substring(0, songname.length()-4);
            
            lbl.setText(songsize); 
            mediaplayer.play(); //play media

            play.setStyle("-fx-background-image:url(\"/musicplayer/pause.png\")");
        } else {
            control = true;
            ctime();
            mediaplayer.pause(); //pause media
          
            play.setStyle("-fx-background-image:url(\"/musicplayer/play.png\")");
        }

    }

    
    public void resetit() {
        lenth.setProgress(0);
        mediaplayer.seek(Duration.seconds(0)); //reset media
      
    }

    public void prviousone() {
        control = true;
        if (songnumb <= songs.size() - 1 && songnumb > 0) {
            songnumb--;

            mediaplayer.stop();

            media = new Media(songs.get(songnumb).toURI().toString());
            mediaplayer = new MediaPlayer(media);

          
          
            playit();
        } else {
            songnumb = 9;

            mediaplayer.stop();

            media = new Media(songs.get(songnumb).toURI().toString());
            mediaplayer = new MediaPlayer(media);

          
           
            playit();
        }
    }

    public void nextone() {
        control = true;
        if (songnumb < songs.size() - 1) {  // check nextsong is avalible
            songnumb++;                 //increse the index for next song

            mediaplayer.stop(); //stop the current media(song)
          
            media = new Media(songs.get(songnumb).toURI().toString());
            mediaplayer = new MediaPlayer(media);

           
           
            playit();
        } else {
            songnumb = 0;                 //increse the index for next song

            mediaplayer.stop(); //stop the current media(song)
            
            media = new Media(songs.get(songnumb).toURI().toString());
            mediaplayer = new MediaPlayer(media);

           
           
            playit();
        }
    }

    public void btime() {
        time = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                playing = true;
                double currenttime = mediaplayer.getCurrentTime().toSeconds();
                double fulltime = media.getDuration().toSeconds();
              

               
                lenth.setProgress(currenttime / fulltime);
                if (currenttime / fulltime == 1) {
                    ctime();
                }
            }

        };

        time.scheduleAtFixedRate(task, 0, 1000);
    }

   
   

    public void ctime() {

        playing = false;
        time.cancel();

    }

    public void speedup(ActionEvent event) {
        if (speed.getValue() == null) {
            mediaplayer.setRate(1);
        } else {
            mediaplayer.setRate(Integer.parseInt(speed.getValue()) * 0.01);
        }

    }

}
