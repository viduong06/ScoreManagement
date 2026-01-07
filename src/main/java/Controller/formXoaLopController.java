/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.KhoaDAO;
import Database.LopDAO;
import Database.qlyGiangVienDAO;
import Database.qlyKhoaDAO;
import Database.qlyLopDao;
import Model.Khoa;
import Model.Lop;
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
public class formXoaLopController {

    qlyKhoaDAO dbKhoa = new qlyKhoaDAO();
    qlyGiangVienDAO dbGV = new qlyGiangVienDAO(dbKhoa);
    LopDAO dbLop = new LopDAO();
    List<Lop> list = dbLop.selectClass(dbGV);
    @FXML
    private TextField txtMaLop;
    @FXML
    private Label lblStatus;
    private String result = "";

    public String getResult() {
        return result;  // controller chính sẽ gọi để lấy dữ liệu
    }

    @FXML
    private void clickOnOK(ActionEvent event) {
        String maLop = txtMaLop.getText();

        if (!maLop.trim().isBlank()) {
            if (Lop.tonTaiMaLop(list, maLop)) {
                result = maLop;
                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            } else {
                lblStatus.setText("Mã lớp không tồn tại!");
                lblStatus.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(b -> lblStatus.setText(""));
                pause.play();
            }
        } else {
            lblStatus.setText("Mã lớp không được để trống");
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
