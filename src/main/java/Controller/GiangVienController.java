/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author quochuy
 */
public class GiangVienController implements Initializable {
    @FXML
    private Button Menu;
    @FXML
    private AnchorPane slider;
    @FXML
    private Button qlySinhVien;
    @FXML
    private Button diemThi;
    @FXML
    private Button ThongKe;
    @FXML
    private AnchorPane contentPane;
    private void addHover(Node node, double distanceX, double distanceY) {
    node.setOnMouseEntered(e -> {
        TranslateTransition slide = new TranslateTransition(Duration.millis(200), node);
        slide.setToX(distanceX);
        slide.setToY(distanceY);
        slide.play();
    });
    node.setOnMouseExited(e -> {
        TranslateTransition slide = new TranslateTransition(Duration.millis(200), node);
        slide.setToX(0);
        slide.setToY(0);
        slide.play();
    });
}
    @FXML
    public void ShowQuanLySinhVien(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/MenuGiangVien/qlySVMenuFXML.fxml"));
            contentPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    @FXML
    public void ShowDiemThi(ActionEvent event){
          try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/MenuGiangVien/diemThiFXML.fxml"));
            contentPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void clickOnLogout(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận đăng xuất");
        alert.setHeaderText("Bạn có muốn đăng xuất?");
        alert.setContentText("Lựa chọn của bạn: ");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu/dangNhapFXML.fxml"));
                Scene scene = new Scene(root);
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    }
    
    @FXML
    public void clickOnThongKe(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/MenuGiangVien/bieuDoTronFXML.fxml"));
            contentPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        slider.setTranslateX(-270);
        Menu.setOnMouseEntered(event->{
           TranslateTransition slide = new TranslateTransition(Duration.millis(200), slider);
           slide.setToX(0);
           slide.play();
        });
        Menu.setOnMouseClicked(event->{
            TranslateTransition slide = new TranslateTransition(Duration.millis(200), slider);
            slide.setToX(-270);
            slide.play();
        });
        addHover(qlySinhVien,10,-5);
        addHover(diemThi,10,-5);
        addHover(ThongKe,10,-5);
    }
}
