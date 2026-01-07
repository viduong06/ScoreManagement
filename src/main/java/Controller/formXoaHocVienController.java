/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.ExcelUtil;
import Database.LoginDAO;
import Database.LopDAO;
import Database.SinhVienDAO;
import Database.qlyGiangVienDAO;
import Database.qlyKhoaDAO;
import Model.DangNhap;
import Model.GiangVien;
import Model.Lop;
import Model.SinhVien;
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
public class formXoaHocVienController {

    LopDAO lopDAO = new LopDAO();
    qlyKhoaDAO qlk = new qlyKhoaDAO();
    qlyGiangVienDAO qlgv = new qlyGiangVienDAO(qlk);
    List<Lop> listLop = lopDAO.selectClass(qlgv);
    List<Lop> dsLopQly = lopDAO.selectClassesByTeacherID(DangNhap.getUsername(), qlgv);
    @FXML
    private TextField txtMSSV;
    @FXML
    private Label lblStatus;
    @FXML
    private TextField txtMaLop;
    private String result = "";

    public String getResult() {
        return result;  // controller chính sẽ gọi để lấy dữ liệu
    }

    @FXML
    private void clickOnOK(ActionEvent event) {
        String MSSV = txtMSSV.getText();
        String maLop = txtMaLop.getText();
        SinhVienDAO svDAO = new SinhVienDAO();
        List<SinhVien> listSV = svDAO.selectStudent();
        if (MSSV.trim().isBlank() && !maLop.trim().isBlank()) {
            lblStatus.setText("Vui lòng nhập đầy đủ thông tin!");
            lblStatus.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(b -> lblStatus.setText(""));
            pause.play();
        }
        if (!SinhVien.tonTaiMSSV(listSV, MSSV)) {
            lblStatus.setText("Mã sinh viên không tồn tại!");
            lblStatus.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(b -> lblStatus.setText(""));
            pause.play();
        }
        if (!Lop.tonTaiMaLop(listLop, maLop)) {
            lblStatus.setText("Mã lớp không tồn tại!");
            lblStatus.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(b -> lblStatus.setText(""));
            pause.play();
        }
        for (SinhVien sv : listSV) {
            if (sv.getLopChuQuan().trim().equals(maLop)) {
                if (sv.getMssv().trim().equals(MSSV)) {
                    result = MSSV;
                    String tk = MSSV + "@st.huce.edu.vn";
                    LoginDAO dltAccount = new LoginDAO();
                    dltAccount.deleteAccount(tk);
                    ExcelUtil.capNhatExcelKhiXoaSV(maLop, MSSV);
                    ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
                    break;
                }
            } else {
                lblStatus.setText("Sinh viên không thuộc lớp này!");
                lblStatus.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(b -> lblStatus.setText(""));
                pause.play();
            }
        }
    }

    @FXML
    private void clickOnCancel(ActionEvent event) {
        result = "";
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}
