/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package videoplayer;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import static javafx.scene.media.MediaPlayer.Status.PLAYING;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 *
 * @author Sushil
 */
public class FXMLDocumentController implements Initializable {
    private String path;
    private MediaPlayer mediaPlayer;
    int minuteD =0;
    int secondD=0;
    int hourD=0;
    
    @FXML
    private MediaView mediaView;
    
    @FXML
    private Button openFile;
    
    @FXML
    private Slider volumeSlider;
    
    @FXML
    private Slider mediaSlider;
    
    @FXML
    private Label label;
    
    @FXML
    private Text time;
    
    @FXML
    private StackPane pane;
    private List<String> extensions;
    @FXML
    private void OpenFileMethod(ActionEvent event) {
        extensions= Arrays.asList("*.mp4","*.3gp","*.mkv","*.MP4","*.MKV","*.3GP","*.flv","*.wmv");
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("select file",extensions);
        
        try{
            fileChooser.getExtensionFilters().add(filter);
            fileChooser.setTitle("Select File to Open");
            File file = fileChooser.showOpenDialog(null);
            path = file.toURI().toString();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        if(path!= null){
            Media media = new Media(path);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
                DoubleProperty width = mediaView.fitWidthProperty();
                DoubleProperty height = mediaView.fitHeightProperty();
                
                width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
                height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));
                
                volumeSlider.setValue(mediaPlayer.getVolume()*100);
                
                
                
                volumeSlider.valueProperty().addListener(new InvalidationListener(){
                    @Override
                    public void invalidated(Observable obeservable){
                        mediaPlayer.setVolume(volumeSlider.getValue()/100);
                    } 
                });
                
                // time duration
                 
                mediaPlayer.currentTimeProperty().addListener(new ChangeListener<javafx.util.Duration>() {
                @Override
                public void changed(ObservableValue<? extends javafx.util.Duration> observable, javafx.util.Duration oldValue, javafx.util.Duration newValue) {
                    mediaSlider.setValue(newValue.toSeconds());
                    
                   // time elapsed and total duration 
                    //long duration = (long)media.getDuration().toSeconds();
                    int hour = (int)mediaPlayer.getCurrentTime().toHours();
                    int minute = (int)mediaPlayer.getCurrentTime().toMinutes();
                    int second = (int)mediaPlayer.getCurrentTime().toSeconds();
                    if(second>59) second = second%60;
                    if(minute>59) minute = minute%60;
                    if(hour>0||hourD>0)
                    {
                        //System.out.println(hour+":"+minute+":"+second);
                        String showTime = hour+":"+minute+":"+second+"/"+hourD+":"+minuteD+":"+secondD;
                        time.setText(showTime);
                    }
                    else
                    {
                       // System.out.println(minute+":"+second);
                        String showTime = minute+":"+second+"/"+minuteD+":"+secondD;
                        time.setText(showTime);
                    }
                  }
                });
                
                mediaSlider.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(javafx.util.Duration.seconds(mediaSlider.getValue()));
                }
            });
            
            mediaSlider.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(javafx.util.Duration.seconds(mediaSlider.getValue()));
                }
            });
            
            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    javafx.util.Duration total = media.getDuration();
                    mediaSlider.setMax(total.toSeconds());
                    // Calculating time of the video duratioin 
                    hourD = (int)media.getDuration().toHours();
                    minuteD = (int)media.getDuration().toMinutes();
                    secondD = (int)media.getDuration().toSeconds();
                    if(secondD>59) secondD = secondD%60;
                    if(minuteD>59) minuteD = minuteD%60;
                }
            });
                
            
            mediaPlayer.play();
        }
    }
    
    @FXML
    private void pauseVideo(ActionEvent event){
        try{
            if(mediaPlayer.getStatus() == PLAYING){
                mediaPlayer.pause();
            }else{ 
                mediaPlayer.play();
                mediaPlayer.setRate(1);
            }
        }catch(Exception e){
            //System.out.println("Open up a file First");
            JOptionPane.showMessageDialog(null,"Open up a File First");
        }
        
    }
    
     @FXML
    private void stopVideo(ActionEvent event){
        try{
        mediaPlayer.stop();
        }catch(NullPointerException e ){
            //System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"Open up a File First");
        }
    }
    
    @FXML
    private void fastVideo(ActionEvent event){
        try{
            mediaPlayer.setRate(mediaPlayer.getRate()+0.5);
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null,"Open up a File First");
        }   
    }
    
    @FXML
    private void slowVideo(ActionEvent event){
        try{
            mediaPlayer.setRate(mediaPlayer.getRate()-0.5);
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null,"Open up a File First");
        }
        
    }
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
