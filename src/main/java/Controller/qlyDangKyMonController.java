/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.DangKyMonHocDAO;
import Database.GiangVienDAO;
import Database.MonHocDAO;
import Database.qlyKhoaDAO;
import Model.DangNhap;
import Model.GiangVien;
import Model.Khoa;
import Model.MonHoc;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author quochuy
 */
public class qlyDangKyMonController implements Initializable {

    @FXML
    private TextField txtTimKiem;

    @FXML
    private TableView tableMonHoc;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableColumn colMaMon;
    @FXML
    private TableColumn colTenMon;
    @FXML
    private TableColumn colSoTinChi;
    @FXML
    private TableColumn colMaKhoa;
    private HashMap<String, MonHoc> danhSachMonHoc;

    public void loadTableMonHoc() {
        MonHocDAO mhDAO = new MonHocDAO();
        qlyKhoaDAO qlk = new qlyKhoaDAO();
        List<MonHoc> list = mhDAO.selectSubject(qlk);
        tableMonHoc.setItems(FXCollections.observableArrayList(list));
        if (danhSachMonHoc == null) {
            danhSachMonHoc = new HashMap<>();
        }
        danhSachMonHoc.clear();
        if (list != null) {
            for (MonHoc mh : list) {
                danhSachMonHoc.put(mh.getMaMonHoc().trim().toUpperCase(), mh);
            }
        }
    }

    @FXML
    private void clickOnDangKyMon(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuSinhVien/formDangKyMonFXML.fxml"));
            Parent root = loader.load();

            formDangKyMonController formController = loader.getController();

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Đăng ký môn");
            dialog.showAndWait();  // đợi form đóng

            // Lấy kết quả sau khi dialog đóng
            String maMonHoc = formController.getResult();
            if (maMonHoc != null) {
                DangKyMonHocDAO dkMon = new DangKyMonHocDAO();
                dkMon.insertStudentEnrollment(DangNhap.getUsername(), maMonHoc);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickOnTimKiem(ActionEvent event) {
        String timKiem = txtTimKiem.getText().trim();
        if (!timKiem.equals("")) {
            String find = timKiem.toUpperCase();
            MonHoc mh = danhSachMonHoc.get(find); // Tìm kiếm trực tiếp trong Map
            if (mh != null) {
                tableMonHoc.setItems(FXCollections.observableArrayList(mh));
            } else {
                tableMonHoc.setItems(FXCollections.observableArrayList());
            }
        } else {
            loadTableMonHoc();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        danhSachMonHoc = new HashMap<>();
        anchorPane.setTranslateY(-270);
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), anchorPane);
        slideIn.setFromY(-270);
        slideIn.setToY(0);
        slideIn.play();
        colMaMon.setCellValueFactory(new PropertyValueFactory<>("maMonHoc"));
        colTenMon.setCellValueFactory(new PropertyValueFactory<>("tenMonHoc"));
        colSoTinChi.setCellValueFactory(new PropertyValueFactory<>("soTinChi"));
        colMaKhoa.setCellValueFactory(new PropertyValueFactory<>("maKhoa"));
        loadTableMonHoc();
    }
}
