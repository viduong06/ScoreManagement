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
public class AdminController implements Initializable {
    @FXML
    private Button Menu;
    @FXML
    private AnchorPane slider;
    @FXML
    private Button qlyKhoa;
    @FXML
    private Button qlyMonHoc;
    @FXML
    private Button qlyNganhHoc;
    @FXML
    private Button qlyLopHoc;
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
    public void ShowQuanLyKhoa(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/MenuAdmin/qlyKhoaFXML.fxml"));
            contentPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void ShowQuanLyNganhHoc(ActionEvent event){
          try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/MenuAdmin/qlyNganhFXML.fxml"));
            contentPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void ShowQuanLyLopHoc(ActionEvent event){
          try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/MenuAdmin/qlyLopFXML.fxml"));
            contentPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void ShowQuanLyGiangVien(ActionEvent event){
          try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/MenuAdmin/qlyGiangVienFXML.fxml"));
            contentPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void ShowQuanLyMonHoc(ActionEvent event){
          try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/MenuAdmin/qlyMonHocFXML.fxml"));
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
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
                Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu/dangNhapFXML.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
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
        addHover(qlyKhoa,10,-5);
        addHover(qlyNganhHoc,10,-5);
        addHover(qlyLopHoc,10,-5);
        addHover(qlyMonHoc,10,-5);
    }
    
}
