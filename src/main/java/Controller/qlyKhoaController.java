/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.KhoaDAO;
import Database.qlyKhoaDAO;
import Model.Khoa;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
public class qlyKhoaController implements Initializable {

    KhoaDAO Khoa = new KhoaDAO();
    @FXML
    private TableView tableKhoa;
    @FXML
    private TableColumn<Khoa, String> colMaKhoa;
    @FXML
    private TableColumn<Khoa, String> colTenKhoa;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField txtTimKiem;

    private HashMap<String, Khoa> danhSachKhoa;

    public void loadTableKhoa() {
        List<Khoa> list = Khoa.selectDepartments();
        tableKhoa.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    private void clickOnThemKhoa(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuAdmin/formThemKhoaFXML.fxml"));
            Parent root = loader.load();

            formThemKhoaController formController = loader.getController();

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Thêm Khoa");
            dialog.showAndWait();  // đợi form đóng

            // Lấy kết quả sau khi dialog đóng
            Khoa khoaMoi = formController.getResult();
            KhoaDAO khoaDAO = new KhoaDAO();
            if (khoaMoi != null) {
                // Gọi DAO/Service để lưu database
                khoaDAO.insertNewDepartment(khoaMoi);
                loadTableKhoa();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickOnXoaKhoa(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuAdmin/formXoaKhoaFXML.fxml"));
            Parent root = loader.load();

            formXoaKhoaController formController = loader.getController();

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Xoá Khoa");
            dialog.showAndWait();  // đợi form đóng

            // Lấy kết quả sau khi dialog đóng
            String maKhoa = formController.getResult();
            KhoaDAO khoaDAO = new KhoaDAO();
            if (!maKhoa.trim().equals("")) {
                // Gọi DAO/Service để lưu database
                khoaDAO.deleteDepartment(maKhoa);
                loadTableKhoa();
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
            Khoa khoa1 = danhSachKhoa.get(find);
            tableKhoa.setItems(FXCollections.observableArrayList(khoa1));
        } else {
            loadTableKhoa();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources
    ) {
        List<Khoa> list = Khoa.selectDepartments();
        danhSachKhoa = new HashMap<>();
        for (Khoa khoa : list) {
            danhSachKhoa.put(khoa.getMaKhoa().trim().toUpperCase(), khoa);
        }
        anchorPane.setTranslateY(-270);
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), anchorPane);
        slideIn.setFromY(-270);
        slideIn.setToY(0);
        slideIn.play();
        colMaKhoa.setCellValueFactory(new PropertyValueFactory<>("maKhoa"));
        colTenKhoa.setCellValueFactory(new PropertyValueFactory<>("tenKhoa"));
        loadTableKhoa();

    }

}
