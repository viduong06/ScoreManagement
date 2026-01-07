/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.KhoaDAO;
import Database.NganhDAO;
import Database.qlyKhoaDAO;
import Model.Khoa;
import Model.Nganh;
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
public class formXoaNganhController {
    NganhDAO dbNganh = new NganhDAO();
    qlyKhoaDAO qlyKhoa = new qlyKhoaDAO();
    List<Nganh> list = dbNganh.selectMajor(qlyKhoa);
    @FXML
    private TextField txtMaNganh;
    @FXML
    private Label lblStatus;
    private String result = "";

    public String getResult() {
        return result;  // controller chính sẽ gọi để lấy dữ liệu
    }

    @FXML
    private void clickOnOK(ActionEvent event) {
        String maNganh = txtMaNganh.getText();

        if (!maNganh.trim().isBlank()) {
            if (Nganh.tonTaiMaNganh(list, maNganh)) {
                result = maNganh;
                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            } else {
            lblStatus.setText("Mã ngành không tồn tại!");
            lblStatus.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(b -> lblStatus.setText(""));
            pause.play();
            }
        } else {
            lblStatus.setText("Mã khoa không được để trống");
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
