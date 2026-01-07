/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.DangKyMonHocDAO;
import Database.MonHocDAO;
import Database.qlyKhoaDAO;
import Model.DangNhap;
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
public class qlyDanhSachDangKyController implements Initializable {

    DangKyMonHocDAO dkMon = new DangKyMonHocDAO();
    private HashMap<String, MonHoc> danhSachDangKy;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView tableDanhSachLop;
    @FXML
    private TableColumn colMaMon;
    @FXML
    private TableColumn colTenMon;
    @FXML
    private TableColumn colSoTinChi;
    @FXML
    private TableColumn colMaKhoa;
    @FXML
    private TextField txtTimKiem;

    public void loadTableMonHoc() {
        List<MonHoc> list = dkMon.selectSubjectIDByStudentID(DangNhap.getUsername());
        tableDanhSachLop.setItems(FXCollections.observableArrayList(list));
        danhSachDangKy.clear();
        for (MonHoc mh : list) {
            danhSachDangKy.put(mh.getMaMonHoc().trim().toUpperCase(), mh);
        }
    }

    @FXML
    public void clickOnHuyDangKy(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuAdmin/formXoaMonHocFXML.fxml"));
            Parent root = loader.load();

            formXoaMonHocController formController = loader.getController();

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Huỷ đăng ký");
            dialog.showAndWait();  // đợi form đóng

            // Lấy kết quả sau khi dialog đóng
            String maMonHoc = formController.getResult();
            if (!maMonHoc.trim().equals("")) {
                // Gọi DAO/Service để lưu database
                dkMon.deleteStudentEnrollment(DangNhap.getUsername(), maMonHoc);
                loadTableMonHoc();
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
            MonHoc mh = danhSachDangKy.get(find);
            if (mh != null) {
                tableDanhSachLop.setItems(FXCollections.observableArrayList(mh));
            } else {
                tableDanhSachLop.setItems(FXCollections.observableArrayList());
            }
        } else {
            loadTableMonHoc();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        danhSachDangKy = new HashMap<>();
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
