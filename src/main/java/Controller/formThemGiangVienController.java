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
public class formThemGiangVienController {

    GiangVienDAO gvDAO = new GiangVienDAO();
    KhoaDAO khoaDAO = new KhoaDAO();
    qlyKhoaDAO qlKhoa = new qlyKhoaDAO();
    List<GiangVien> list = gvDAO.selectTeacher(qlKhoa);
    @FXML
    private TextField txtMaGiangVien;
    @FXML
    private TextField txtTenGiangVien;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtSDT;
    @FXML
    private TextField txtMaKhoa;
    @FXML
    private Label lblStatus;

    private GiangVien result = null;

    public GiangVien getResult() {
        return result;  // controller chính sẽ gọi để lấy dữ liệu
    }

    @FXML
    private void clickOnOK(ActionEvent event) {
        String maGiangVien = txtMaGiangVien.getText();
        String tenGiangVien = txtTenGiangVien.getText();
        String email = txtEmail.getText();
        String SDT = txtSDT.getText();
        String maKhoa = txtMaKhoa.getText();
        List<Khoa> listKhoa = khoaDAO.selectDepartments();

        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        String sdtRegex = "^(0|\\+84)\\d{9}$"; // SDT 10 số (bắt đầu bằng 0 hoặc +84)

        if (maGiangVien.isBlank() || tenGiangVien.isBlank() || email.isBlank() || SDT.isBlank() || maKhoa.isBlank()) {
            lblStatus.setText("Vui lòng điền đầy đủ thông tin");
            lblStatus.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(b -> lblStatus.setText(""));
            pause.play();
        }
        if (!email.matches(emailRegex)) {
            lblStatus.setText("Email không đúng định dạng!");
            lblStatus.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(b -> lblStatus.setText(""));
            pause.play();
        } else if (!SDT.matches(sdtRegex)) {
            lblStatus.setText("Số điện thoại không đúng định dạng 10 số");
            lblStatus.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(b -> lblStatus.setText(""));
            pause.play();
        } else if (!Khoa.tonTaiMaKhoa(listKhoa, maKhoa)) {
            lblStatus.setText("Mã khoa không tồn tại");
            lblStatus.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(b -> lblStatus.setText(""));
            pause.play();
        }
        for (GiangVien gv : list) {
            if (gv.getMaGiangVien().trim().equals(maGiangVien)) {
                lblStatus.setText("Giảng viên đã có trong danh sách!");
                lblStatus.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(b -> lblStatus.setText(""));
                pause.play();
                break;
            }
        }
        for (Khoa khoa : listKhoa) {
            if (khoa.getMaKhoa().trim().equals(maKhoa)) {
                result = new GiangVien(maGiangVien, tenGiangVien, email, SDT, maKhoa);
                String tk = maGiangVien;
                String mk = maGiangVien + "hucegv";
                String role = "GiangVien";
                LoginDAO insertAcc = new LoginDAO();
                insertAcc.insertNewAccount(tk, mk, role);
                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
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
