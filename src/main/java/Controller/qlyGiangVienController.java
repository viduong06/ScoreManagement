/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.GiangVienDAO;
import Database.KhoaDAO;
import Database.qlyKhoaDAO;
import Model.GiangVien;
import Model.Khoa;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Dialog;
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
public class qlyGiangVienController implements Initializable {

    qlyKhoaDAO qlKhoaDAO = new qlyKhoaDAO();
    @FXML
    private AnchorPane anchorPane;
    GiangVienDAO gvDAO = new GiangVienDAO();
    @FXML
    private TableView tableGiangVien;
    @FXML
    private TableColumn<GiangVien, String> colMaGiangVien;
    @FXML
    private TableColumn<GiangVien, String> colTenGiangVien;
    @FXML
    private TableColumn<GiangVien, String> colEmail;
    @FXML
    private TableColumn<GiangVien, String> colSDT;
    @FXML
    private TableColumn<GiangVien, String> colMaKhoa;
    @FXML
    private TextField txtTimKiem;

    private HashMap<String, GiangVien> danhSachGiangVien;

    public void loadTableGiangVien() {
        List<GiangVien> list = gvDAO.selectTeacher(qlKhoaDAO);
        tableGiangVien.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    private void clickOnThemGiangVien(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuAdmin/formThemGiangVienFXML.fxml"));
            Parent root = loader.load();

            formThemGiangVienController formController = loader.getController();

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Thêm giảng viên");
            dialog.showAndWait();  // đợi form đóng

            // Lấy kết quả sau khi dialog đóng
            GiangVien GiangVienMoi = formController.getResult();
            GiangVienDAO GiangVienDAO = new GiangVienDAO();
            if (GiangVienMoi != null) {
                // Gọi DAO/Service để lưu database
                GiangVienDAO.insertNewTeacher(GiangVienMoi, formController.getTxtMaKhoa());
                loadTableGiangVien();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickOnXoaGiangVien(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuAdmin/formXoaGiangVienFXML.fxml"));
            Parent root = loader.load();

            formXoaGiangVienController formController = loader.getController();

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Xoá giảng viên");
            dialog.showAndWait();  // đợi form đóng

            // Lấy kết quả sau khi dialog đóng
            String maGiangVien = formController.getResult();
            GiangVienDAO giangVienDAO = new GiangVienDAO();
            if (!maGiangVien.trim().equals("")) {
                // Gọi DAO/Service để lưu database
                giangVienDAO.deleteTeacher(maGiangVien);
                loadTableGiangVien();
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
            GiangVien gv = danhSachGiangVien.get(find);
            tableGiangVien.setItems(FXCollections.observableArrayList(gv));
        } else {
            loadTableGiangVien();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<GiangVien> list = gvDAO.selectTeacher(qlKhoaDAO);
        danhSachGiangVien = new HashMap<>();
        for (GiangVien gv : list) {
            danhSachGiangVien.put(gv.getMaGiangVien().trim().toUpperCase(), gv);
        }
        anchorPane.setTranslateY(-270);
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), anchorPane);
        slideIn.setFromY(-270);
        slideIn.setToY(0);
        slideIn.play();
        colMaGiangVien.setCellValueFactory(new PropertyValueFactory<>("maGiangVien"));
        colTenGiangVien.setCellValueFactory(new PropertyValueFactory<>("tenGiangVien"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSDT.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        colMaKhoa.setCellValueFactory(new PropertyValueFactory<>("maKhoa"));
        loadTableGiangVien();
    }

}
