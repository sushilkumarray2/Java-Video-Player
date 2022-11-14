/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package videoplayer;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author Sushil
 */
public class VideoPlayer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Video Player");
        Image icon = new Image(getClass().getResourceAsStream("icons/icon.png"));
       stage.getIcons().add(icon);
        
        stage.setFullScreenExitHint("Press ESC or Double click to exit the full Screen");
        scene.setOnMouseClicked(new EventHandler <MouseEvent>(){
            @Override
            public void handle(MouseEvent clicked) {
                if(clicked.getClickCount() == 2){
                    if(!stage.isFullScreen()){
                        stage.setFullScreen(true);
                    }else{
                        stage.setFullScreen(false);
                    }
                    
                }
            }
            
        });
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
