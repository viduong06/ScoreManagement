/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.DangKyLopHocDAO;
import Database.ExcelUtil;
import Database.LopDAO;
import Database.NganhDAO;
import Database.SinhVienDAO;
import Database.qlyGiangVienDAO;
import Database.qlyKhoaDAO;
import Model.DangNhap;
import Model.GiangVien;
import Model.Khoa;
import Model.Lop;
import Model.SinhVien;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author quochuy
 */
public class qlySinhVienController implements Initializable {

    SinhVienDAO svDAO = new SinhVienDAO();
    LopDAO lopDAO = new LopDAO();
    qlyKhoaDAO qlk = new qlyKhoaDAO();
    qlyGiangVienDAO qlgv = new qlyGiangVienDAO(qlk);
    List<Lop> dsLopQly = lopDAO.selectClassesByTeacherID(DangNhap.getUsername(), qlgv);
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView tableSinhVien;
    @FXML
    private TableColumn colMSSV;
    @FXML
    private TableColumn colTen;
    @FXML
    private TableColumn colNgaySinh;
    @FXML
    private TableColumn colMaLop;
    @FXML
    private TextField txtTimKiem;
    @FXML
    private ComboBox<String> cbMaLop;

    public void loadTableSinhVien() {
        String maLop = cbMaLop.getValue();
        List<SinhVien> list = svDAO.selectStudentByClassID(maLop);
        tableSinhVien.setVisible(true);
        tableSinhVien.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    public void chooseClass(ActionEvent event) {
        loadTableSinhVien();
    }

    @FXML
    public void clickOnThemHocVien(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuGiangVien/formThemHocVien.fxml"));
            Parent root = loader.load();

            formThemSinhVienController formController = loader.getController();

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Thêm sinh viên");
            dialog.showAndWait();

            SinhVien svMoi = formController.getResult();

            if (svMoi != null) {
                svDAO.insertNewStudent(svMoi);
                
                // Đăng ký sinh viên vào lớp học phần (lấy từ form thêm sinh viên)
                String maLopHocPhan = svMoi.getLopChuQuan();
                if (maLopHocPhan != null && !maLopHocPhan.isBlank()) {
                    DangKyLopHocDAO dkDAO = new DangKyLopHocDAO();
                    dkDAO.insertStudentEnrollment(svMoi.getMssv(), maLopHocPhan);
                }
                
                for (Lop lop : dsLopQly) {
                    ExcelUtil.themDuLieu(lop.getMaLop());
                }
                loadTableSinhVien();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickOnXoaHocVien(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuGiangVien/formXoaHocVienFXML.fxml"));
            Parent root = loader.load();

            formXoaHocVienController formController = loader.getController();

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Xoá sinh viên");
            dialog.showAndWait();

            String mssv = formController.getResult();
            
            if (!mssv.trim().equals("")) {
                svDAO.deleteStudent(mssv);
                
                loadTableSinhVien();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickOnTimKiem(ActionEvent event) {
        String timKiem = txtTimKiem.getText().trim();
        String maLop = cbMaLop.getValue();
        List<SinhVien> list = svDAO.selectStudentByClassID(maLop);
        boolean flag = false;
        if (!timKiem.equals("")) {
            for (SinhVien sv : list) {
                if (sv.getMssv().trim().toUpperCase().equals(timKiem)) {
                    tableSinhVien.setItems(FXCollections.observableArrayList(sv));
                    flag = true;
                }
            }
            if (!flag) {
                tableSinhVien.setItems(FXCollections.observableArrayList());
            }
        } else {
            loadTableSinhVien();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPane.setTranslateY(-270);
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), anchorPane);
        slideIn.setFromY(-270);
        slideIn.setToY(0);
        slideIn.play();

        for (Lop lop : dsLopQly) {
            cbMaLop.getItems().add(lop.getMaLop());

        }
        colMSSV.setCellValueFactory(new PropertyValueFactory<>("mssv"));
        colTen.setCellValueFactory(new PropertyValueFactory<>("ten"));
        colNgaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        colMaLop.setCellValueFactory(new PropertyValueFactory<>("lopChuQuan"));
    }
}
