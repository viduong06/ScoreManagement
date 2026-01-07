/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.LoginDAO;
import Model.DangNhap;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author quochuy
 */
public class dangNhapAdminController implements Initializable {
    LoginDAO dbLogin = new LoginDAO();
    private String role = "Admin";
    @FXML
    private Label lblStatus;
    @FXML
    private TextField txtTaiKhoan;
    @FXML
    private PasswordField txtMatKhau;
    @FXML
    public void clickOnDangNhap(ActionEvent event){
        String tk = txtTaiKhoan.getText();
        String mk = txtMatKhau.getText();
        if(dbLogin.checkLogin(tk, mk, role)){
         try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/MenuAdmin/MainMenuFXML.fxml"));
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
                    PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                    pause.setOnFinished(b -> lblStatus.setText(""));
                    pause.play();
        } else {
                    lblStatus.setText("Tên đăng nhập hoặc mật khẩu không hợp lệ");
                    lblStatus.setVisible(true);
                    PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                    pause.setOnFinished(b -> lblStatus.setText(""));
                    pause.play();
        }
    }
    @FXML
    public void clickOnDanhChoSinhVien(ActionEvent event){
         try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu/loginSinhVienFXML.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();            
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void clickOnDanhChoGiangVien(ActionEvent event){
         try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu/loginGiangVienFXML.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();            
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
   
}
