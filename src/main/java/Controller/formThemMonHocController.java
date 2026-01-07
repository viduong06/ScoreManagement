/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.KhoaDAO;
import Database.LoginDAO;
import Model.GiangVien;
import Model.Khoa;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author quochuy
 */
public class formThemMonHocController implements Initializable {
    KhoaDAO khoaDAO = new KhoaDAO();
    List<Khoa> dsKhoa = khoaDAO.selectDepartments();
    @FXML
    private TextField txtMaMonHoc;
    @FXML
    private TextField txtTenMonHoc;
    @FXML
    private ComboBox<String> cbMaKhoa;
    @FXML
    private ComboBox<Integer> cbSoTinChi;
    @FXML
    private Label lblStatus;
    private MonHoc result = null;

    public MonHoc getResult() {
        return result;  // controller chính sẽ gọi để lấy dữ liệu
    }

    @FXML
    private void clickOnOK(ActionEvent event) {
        String maMonHoc = txtMaMonHoc.getText();
        String tenMonHoc = txtTenMonHoc.getText();
        String maKhoa = cbMaKhoa.getValue();
        int soTinChi = cbSoTinChi.getValue();

        if (maMonHoc.trim().isBlank() || tenMonHoc.trim().isBlank() || maKhoa.trim().isBlank()) {
            lblStatus.setText("Vui lòng nhập đầy đủ thông tin!");
            lblStatus.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(b -> lblStatus.setText(""));
            pause.play();
        } else {
            result = new MonHoc(maMonHoc, tenMonHoc, soTinChi, maKhoa);
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        }
    }

    public String getCbMaKhoa() {
        return cbMaKhoa.getValue();
    }

    @FXML
    private void clickOnCancel(ActionEvent event) {
        result = null;
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbSoTinChi.getItems().add(1);
        cbSoTinChi.getItems().add(2);
        cbSoTinChi.getItems().add(3);
        for(Khoa khoa : dsKhoa){
            cbMaKhoa.getItems().add(khoa.getMaKhoa());
        }
    }
}
