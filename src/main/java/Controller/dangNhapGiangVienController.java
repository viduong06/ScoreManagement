/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.LoginDAO;
import Model.DangNhap;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class dangNhapGiangVienController implements Initializable {
    LoginDAO dbLogin = new LoginDAO();    
    private String role = "GiangVien";
    @FXML
    private Label lblStatus;
    @FXML
    private TextField txtTaiKhoan;
    @FXML
    private PasswordField txtMatKhau;
    @FXML
    private VBox slider;
    @FXML
    private VBox slider1;
    @FXML
    public void clickOnDangNhapGiangVien(ActionEvent event){
        String tk = txtTaiKhoan.getText();
        String mk = txtMatKhau.getText();
        if(dbLogin.checkLogin(tk, mk, role)){
            DangNhap.setUsername(tk);
         try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/MenuGiangVien/MainMenuFXML.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/View/MenuAdmin/style.css").toExternalForm());
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        } else if(tk.trim().isEmpty() || mk.trim().isEmpty()){
                    lblStatus.setText("Tên đăng nhập hoặc mật khẩu không được để trống");
                    lblStatus.setVisible(true);
                    PauseTransition pause = new PauseTransition(Duration.seconds(3));
                    pause.setOnFinished(b -> lblStatus.setText(""));
                    pause.play();
        } else {
                    lblStatus.setText("Tên đăng nhập hoặc mật khẩu không hợp lệ");
                    lblStatus.setVisible(true);
                    PauseTransition pause = new PauseTransition(Duration.seconds(3));
                    pause.setOnFinished(b -> lblStatus.setText(""));
                    pause.play();
        }
    }
    @FXML
    public void clickOnDangKy(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu/dangKyFXML.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("/View/MenuAdmin/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void clickOnTroVe(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu/dangNhapFXML.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/View/MenuAdmin/style.css").toExternalForm());
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();            
            stage.setScene(scene);
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        slider.setTranslateX(1100);
        slider1.setTranslateX(-400);
        TranslateTransition slide = new TranslateTransition(Duration.millis(700), slider);
        slide.setToX(0);
        slide.play();
        TranslateTransition slide1 = new TranslateTransition(Duration.millis(500), slider1);
        slide1.setToX(0);
        slide1.play();
    }

    public String getTxtTaiKhoan() {
        return txtTaiKhoan.getText();
    }
    
}
