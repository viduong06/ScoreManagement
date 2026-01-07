/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.LoginDAO;
import Database.LopDAO;
import Database.SinhVienDAO;
import Database.qlyGiangVienDAO;
import Database.qlyKhoaDAO;
import Model.DangNhap;
import Model.Khoa;
import Model.Lop;
import Model.Nganh;
import Model.SinhVien;
import java.time.LocalDate;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author quochuy
 */
public class formThemSinhVienController {

    LoginDAO themSV = new LoginDAO();
    LopDAO lopDAO = new LopDAO();
    qlyKhoaDAO qlk = new qlyKhoaDAO();
    qlyGiangVienDAO qlgv = new qlyGiangVienDAO(qlk);
    SinhVienDAO svDAO = new SinhVienDAO();
    List<SinhVien> listSV = svDAO.selectStudent();
    List<Lop> listLop = lopDAO.selectClassesByTeacherID(DangNhap.getUsername(), qlgv);

    @FXML
    private TextField txtTen;
    @FXML
    private TextField txtMSSV;
    @FXML
    private DatePicker dtDate;
    @FXML
    private TextField txtMaLop;
    @FXML
    private Label lblStatus;
    private SinhVien result = null;

    public SinhVien getResult() {
        return result;  // controller chính sẽ gọi để lấy dữ liệu
    }

    @FXML
    private void clickOnOK(ActionEvent event) {
        String ten = txtTen.getText();
        String MSSV = txtMSSV.getText();
        String maLop = txtMaLop.getText();
        LocalDate ngaySinh = dtDate.getValue();

        List<Lop> list = lopDAO.selectClass(qlgv);
        if (ten.isBlank() && MSSV.isBlank() && maLop.isBlank() && ngaySinh == null) {
            lblStatus.setText("Vui lòng nhập đầy đủ thông tin");
            lblStatus.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(b -> lblStatus.setText(""));
            pause.play();
        }
        if (!Lop.tonTaiMaLop(list, maLop)) {
            lblStatus.setText("Lớp không tồn tại!");
            lblStatus.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(b -> lblStatus.setText(""));
            pause.play();
        }
        for (SinhVien sv : listSV) {
            if (sv.getMssv().trim().equals(MSSV)) {
                lblStatus.setText("MSSV đã tồn tại!");
                lblStatus.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(b -> lblStatus.setText(""));
                pause.play();
                break;
            }
        }
        boolean flag = false;
        for (Lop lop : listLop) {
            if (lop.getMaLop().trim().equals(maLop)) {
                flag = true;
                result = new SinhVien(MSSV, ten, ngaySinh, maLop);
                String tk = MSSV + "@st.huce.edu.vn";
                String mk = MSSV;
                String role = "SinhVien";
                themSV.insertNewAccount(tk, mk, role);
                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
                return;
            }
        }
        if(!flag){
             lblStatus.setText("Lớp này không thuộc quyền quản lý!");
                lblStatus.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(b -> lblStatus.setText(""));
                pause.play();
                return;
        }
        
    }

    @FXML
    private void clickOnCancel(ActionEvent event) {
        result = null;
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

}
