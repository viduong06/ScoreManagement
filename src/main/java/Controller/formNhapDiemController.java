/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.ExcelUtil;
import Database.LoginDAO;
import Database.LopDAO;
import Database.qlyGiangVienDAO;
import Database.qlyKhoaDAO;
import Model.DangNhap;
import Model.GiangVien;
import Model.Khoa;
import Model.Lop;
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
public class formNhapDiemController implements Initializable {

    LopDAO lopDAO = new LopDAO();
    qlyKhoaDAO qlk = new qlyKhoaDAO();
    qlyGiangVienDAO qlgv = new qlyGiangVienDAO(qlk);
    List<Lop> dsLopQly = lopDAO.selectClassesByTeacherID(DangNhap.getUsername(), qlgv);

    @FXML
    private ComboBox<String> cbMaLop;
    @FXML
    private Label lblStatus;


    @FXML
    private void clickOnOK(ActionEvent event) {
        String maLop = cbMaLop.getValue();

        if (!maLop.isBlank()) {
            ExcelUtil.openExcelByMaLop(maLop);
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } else {
            lblStatus.setText("Vui lòng chọn lớp để nhập điểm");
            lblStatus.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(b -> lblStatus.setText(""));
            pause.play();
        }
    }

    @FXML
    private void clickOnCancel(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Lop lop : dsLopQly) {
            cbMaLop.getItems().add(lop.getMaLop());

        }
    }
}
