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
public class qlyNganhController implements Initializable {

    NganhDAO nganh_DAO = new NganhDAO();
    qlyKhoaDAO qlyKhoa = new qlyKhoaDAO();
    @FXML
    private TableView tableNganh;
    @FXML
    private TableColumn<Nganh, String> colMaNganh;
    @FXML
    private TableColumn<Nganh, String> colTenNganh;
    @FXML
    private TableColumn<Nganh, String> colTenKhoa;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField txtTimKiem;

    private HashMap<String, Nganh> danhSachNganh;

    public void loadTableNganh() {
        List<Nganh> list = nganh_DAO.selectMajor(qlyKhoa);
        tableNganh.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    private void clickOnThemNganh(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuAdmin/formThemNganhFXML.fxml"));
            Parent root = loader.load();

            formThemNganhController formController = loader.getController();

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Thêm Khoa");
            dialog.showAndWait();  // đợi form đóng

            // Lấy kết quả sau khi dialog đóng
            Nganh nganhMoi = formController.getResult();
            if (nganhMoi != null) {
                // Gọi DAO/Service để lưu database
                String maKhoa = formController.getTxtMaKhoa().trim();
                nganh_DAO.insertNewMajor(nganhMoi, maKhoa);
                loadTableNganh();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickOnXoaNganhHoc(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuAdmin/formXoaNganhFXML.fxml"));
            Parent root = loader.load();

            formXoaNganhController formController = loader.getController();

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Xoá Ngành");
            dialog.showAndWait();  // đợi form đóng

            // Lấy kết quả sau khi dialog đóng
            String maNganh = formController.getResult();
            NganhDAO nganhDAO = new NganhDAO();
            if (!maNganh.trim().equals("")) {
                // Gọi DAO/Service để lưu database
                nganhDAO.deleteMajor(maNganh);
                loadTableNganh();
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
            Nganh nganh1 = danhSachNganh.get(find);
            tableNganh.setItems(FXCollections.observableArrayList(nganh1));
        } else {
            loadTableNganh();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Nganh> list = nganh_DAO.selectMajor(qlyKhoa);
        danhSachNganh = new HashMap<>();
        for (Nganh nganh : list) {
            danhSachNganh.put(nganh.getMaNganh().trim().toUpperCase(), nganh);
        }
        anchorPane.setTranslateY(-270);
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), anchorPane);
        slideIn.setFromY(-270);
        slideIn.setToY(0);
        slideIn.play();
        colMaNganh.setCellValueFactory(new PropertyValueFactory<>("maNganh"));
        colTenNganh.setCellValueFactory(new PropertyValueFactory<>("tenNganh"));
        colTenKhoa.setCellValueFactory(new PropertyValueFactory<>("tenKhoa"));
        loadTableNganh();
    }

}
