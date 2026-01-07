/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.GiangVienDAO;
import Database.KhoaDAO;
import Database.LoginDAO;
import Database.qlyKhoaDAO;
import Model.GiangVien;
import Model.Khoa;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author quochuy
 */
public class formXoaGiangVienController {

    qlyKhoaDAO dbKhoa = new qlyKhoaDAO();
    GiangVienDAO dbGiangVien = new GiangVienDAO();
    List<GiangVien> list = dbGiangVien.selectTeacher(dbKhoa);
    @FXML
    private TextField txtMaGiangVien;
    @FXML
    private Label lblStatus;
    private String result = "";

    public String getResult() {
        return result;  // controller chính sẽ gọi để lấy dữ liệu
    }

    @FXML
    private void clickOnOK(ActionEvent event) {
        String maGiangVien = txtMaGiangVien.getText();

        if (!maGiangVien.trim().isBlank()) {
            if (GiangVien.tonTaiMaGiangVien(list, maGiangVien)) {
                result = maGiangVien;
                LoginDAO dltAccount = new LoginDAO();
                dltAccount.deleteAccount(maGiangVien);
                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            } else {
                lblStatus.setText("Mã giảng viên không tồn tại!");
                lblStatus.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(b -> lblStatus.setText(""));
                pause.play();
            }
        } else {
            lblStatus.setText("Mã giảng viên không được để trống");
            lblStatus.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(b -> lblStatus.setText(""));
            pause.play();
        }
    }

    @FXML
    private void clickOnCancel(ActionEvent event) {
        result = "";
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}
