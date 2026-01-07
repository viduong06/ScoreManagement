/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.DatabaseConnect;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author quochuy
 */
public class DangKyGiangVienController implements Initializable {

    LoginDAO dbLogin = new LoginDAO();
    private String role = "GiangVien";
    @FXML
    private VBox slider;
    @FXML
    private VBox slider1;
    @FXML
    private TextField txtTaiKhoanDangKy;
    @FXML
    private PasswordField txtMatKhauDangKy;
    @FXML
    private TextField txtXacNhanMatKhau;
    @FXML
    private Label lblStatusDangKy;

    @FXML
    public void clickOnDangNhap(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu/loginGiangVienFXML.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("/View/MenuAdmin/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickOnDangKy(ActionEvent event) {
        String tk = txtTaiKhoanDangKy.getText();
        String mk = txtMatKhauDangKy.getText();
        String confirm = txtXacNhanMatKhau.getText();
        String regex = "^GV\\d{3}$";
        if (tk.trim().isEmpty()) {
            lblStatusDangKy.setText("Tên đăng nhập không được để trống");
            lblStatusDangKy.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(b -> lblStatusDangKy.setText(""));
            pause.play();
        } else if (mk.trim().isEmpty()) {
            lblStatusDangKy.setText("Mật khẩu không được để trống");
            lblStatusDangKy.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(b -> lblStatusDangKy.setText(""));
            pause.play();
        } else if (!mk.equals(confirm)) {
            lblStatusDangKy.setText("Xác minh mật khẩu không hợp lệ");
            lblStatusDangKy.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(b -> lblStatusDangKy.setText(""));
            pause.play();
        } else {
            if (tk.matches(regex)) {
                if (!dbLogin.checkTK(tk)) {
                    try {
                        dbLogin.insertNewAccount(tk, mk, role);
                        Parent root1 = FXMLLoader.load(getClass().getResource("/View/MainMenu/loginGiangVienFXML.fxml"));
                        Scene scene1 = new Scene(root1);
                        Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene1.getStylesheets().add(getClass().getResource("/View/MenuAdmin/style.css").toExternalForm());
                        stage1.setScene(scene1);
                        stage1.show();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Thông báo");
                        alert.setHeaderText("Tạo thành công");
                        alert.setContentText("Đăng ký thành công");
                        alert.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    lblStatusDangKy.setText("Tên đăng nhập đã tồn tại!");
                    lblStatusDangKy.setVisible(true);
                    PauseTransition pause = new PauseTransition(Duration.seconds(3));
                    pause.setOnFinished(b -> lblStatusDangKy.setText(""));
                    pause.play();
                }
            } else {
                lblStatusDangKy.setText("Tên đăng nhập phải đúng định dạng (VD:GV001)");
                lblStatusDangKy.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                pause.setOnFinished(b -> lblStatusDangKy.setText(""));
                pause.play();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        slider.setTranslateX(1100);
        slider1.setTranslateX(-400);
        TranslateTransition slide = new TranslateTransition(Duration.millis(600), slider);
        slide.setToX(0);
        slide.play();
        TranslateTransition slide1 = new TranslateTransition(Duration.millis(500), slider1);
        slide1.setToX(0);
        slide1.play();
    }
}
