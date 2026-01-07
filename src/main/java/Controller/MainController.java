/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author quochuy
 */
public class MainController extends Application implements Initializable {
    @Override
    public void start(Stage stage) throws Exception {
         try{
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu/dangNhapFXML.fxml"));
            Scene scene1 = new Scene(root);
            stage.setTitle("Menu");
            scene1.getStylesheets().add(getClass().getResource("/View/MenuAdmin/style.css").toExternalForm());
            stage.setScene(scene1);
            stage.setResizable(false);
            stage.show();
            
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
   
}