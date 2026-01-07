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
public class formThemNganhController {

    KhoaDAO Khoa_DAO = new KhoaDAO();
    NganhDAO nganhDAO = new NganhDAO();
    qlyKhoaDAO qlyKhoa = new qlyKhoaDAO();
    List<Nganh> list = nganhDAO.selectMajor(qlyKhoa);
    @FXML
    private TextField txtMaNganh;
    @FXML
    private TextField txtTenNganh;
    @FXML
    private TextField txtMaKhoa;
    @FXML
    private Label lblStatus;
    private Nganh result = null;

    public Nganh getResult() {
        return result;  // controller chính sẽ gọi để lấy dữ liệu
    }

   @FXML
private void clickOnOK(ActionEvent event) {
    String maNganh = txtMaNganh.getText();
    String tenNganh = txtTenNganh.getText();
    String maKhoa = txtMaKhoa.getText();
    List<Khoa> listKhoa = Khoa_DAO.selectDepartments();
    
    if (maKhoa.isBlank() || tenNganh.isBlank() || maNganh.isBlank()) {
        lblStatus.setText("Vui lòng nhập đầy đủ thông tin");
        lblStatus.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(b -> lblStatus.setText(""));
        pause.play();
        return; 
    }
    
    if (!Khoa.tonTaiMaKhoa(listKhoa, maKhoa)) {
        lblStatus.setText("Mã khoa không tồn tại!");
        lblStatus.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(b -> lblStatus.setText(""));
        pause.play();
        return;
    }
    
    for (Nganh nganh : list) {
        if (nganh.getMaNganh().equals(maNganh)) {
            lblStatus.setText("Mã ngành đã tồn tại!");
            lblStatus.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(b -> lblStatus.setText(""));
            pause.play();
            return;
        }
    }
    
    for (Khoa khoa : listKhoa) {
        if (khoa.getMaKhoa().trim().equalsIgnoreCase(maKhoa)) {
            result = new Nganh(maNganh, tenNganh, khoa.getTenKhoa());
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            return; 
        }
    }
}

    @FXML
    private void clickOnCancel(ActionEvent event) {
        result = null;
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public String getTxtMaKhoa() {
        return txtMaKhoa.getText();
    }
}
