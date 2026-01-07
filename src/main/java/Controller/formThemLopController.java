/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.ExcelUtil;
import Database.GiangVienDAO;
import Database.KhoaDAO;
import Database.LopDAO;
import Database.qlyGiangVienDAO;
import Database.qlyKhoaDAO;
import Model.GiangVien;
import Model.Khoa;
import Model.Lop;
import Model.Nganh;
import java.util.ArrayList;
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
 * @author LENOVO
 */
public class formThemLopController {

    GiangVienDAO gvDAO = new GiangVienDAO();
    LopDAO lopDAO = new LopDAO();
    qlyKhoaDAO qlk = new qlyKhoaDAO();
    qlyGiangVienDAO qlgv = new qlyGiangVienDAO(qlk);
    List<Lop> list = lopDAO.selectClass(qlgv);
    @FXML
    private TextField txtMaLop;
    @FXML
    private TextField txtTenLop;
    @FXML
    private TextField txtMaGiangVien;
    @FXML
    private Label lblStatus;

    private Lop result = null;

    public Lop getResult() {
        return result;  // controller chính sẽ gọi để lấy dữ liệu
    }

    @FXML
    private void clickOnOK(ActionEvent event) {
        String maLop = txtMaLop.getText().trim();
        String tenLop = txtTenLop.getText().trim();
        String maGV = txtMaGiangVien.getText().trim();

        if (maLop.isBlank() || tenLop.isBlank() || maGV.isBlank()) {
            lblStatus.setText("Vui lòng nhập đầy đủ thông tin!");
                lblStatus.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(b -> lblStatus.setText(""));
                pause.play();
        }

        qlyKhoaDAO qlyKhoaDAO = new qlyKhoaDAO();
        List<GiangVien> list1 = gvDAO.selectTeacher(qlyKhoaDAO);

        if (!GiangVien.tonTaiMaGiangVien(list1, maGV)) {
           lblStatus.setText("Mã giảng viên không tồn tại!");
                lblStatus.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(b -> lblStatus.setText(""));
                pause.play();
        }

        for (Lop lop : list) {
            if (lop.getMaLop().trim().equals(maLop)) {
               lblStatus.setText("Mã lớp đã tồn tại!");
                lblStatus.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(b -> lblStatus.setText(""));
                pause.play();
            }
        }

        for (GiangVien gv : list1) {
            if (gv.getMaGiangVien().trim().equals(maGV)) {
                result = new Lop(maLop, tenLop, gv.getTenGiangVien(), gv.getSDT());
                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
                break;
            }
        }
    }

    @FXML
    private void clickOnCancel(ActionEvent event) {
        result = null;
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public String getTxtMaGiangVien() {
        return txtMaGiangVien.getText();
    }

    public void setTxtMaGiangVien(TextField txtMaGiangVien) {
        this.txtMaGiangVien = txtMaGiangVien;
    }

}
