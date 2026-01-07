/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.MonHocDAO;
import Database.qlyKhoaDAO;
import Model.MonHoc;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author quochuy
 */
public class formDangKyMonController implements Initializable{
    
     MonHocDAO mhDAO = new MonHocDAO();
    qlyKhoaDAO qlyKhoa = new qlyKhoaDAO();
    List<MonHoc> listMH = mhDAO.selectSubject(qlyKhoa);
    @FXML
    private ComboBox<String> cbMaMonHoc;
    @FXML
    private Label lblStatus;
    String result = "";

    public String getResult() {
        return result;  // controller chính sẽ gọi để lấy dữ liệu
    }

    @FXML
    private void clickOnOK(ActionEvent event) {
        String maMonHoc = cbMaMonHoc.getValue();

        if (maMonHoc.trim().isBlank()) {
            lblStatus.setText("Vui lòng nhập đầy đủ thông tin!");
            lblStatus.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(b -> lblStatus.setText(""));
            pause.play();
        } else {
            result = maMonHoc;
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        }
    }

    @FXML
    private void clickOnCancel(ActionEvent event) {
        result = "";
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (MonHoc mh : listMH) {
            cbMaMonHoc.getItems().add(mh.getMaMonHoc());
        }
    }
}
