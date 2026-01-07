/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.MonHocDAO;
import Database.NganhDAO;
import Database.qlyKhoaDAO;
import Model.GiangVien;
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
public class qlyMonHocController implements Initializable {

    MonHocDAO mhDAO = new MonHocDAO();
    qlyKhoaDAO qlk = new qlyKhoaDAO();
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView tableMonHoc;
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
    
    private HashMap<String, MonHoc> danhSachMonHoc;
    
    public void loadTableMonHoc() {
        List<MonHoc> list = mhDAO.selectSubject(qlk);
        tableMonHoc.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    public void clickOnThemMonHoc(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuAdmin/formThemMonHocFXML.fxml"));
            Parent root = loader.load();

            formThemMonHocController formController = loader.getController();

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Thêm môn học");
            dialog.showAndWait();  // đợi form đóng

            // Lấy kết quả sau khi dialog đóng
            MonHoc monHocMoi = formController.getResult();
            if (monHocMoi != null) {
                // Gọi DAO/Service để lưu database
                mhDAO.insertNewSubject(monHocMoi);
                loadTableMonHoc();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickOnXoaMonHoc(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuAdmin/formXoaMonHocFXML.fxml"));
            Parent root = loader.load();formXoaMonHocController formController = loader.getController();

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Xoá Ngành");
            dialog.showAndWait();  // đợi form đóng

            // Lấy kết quả sau khi dialog đóng
            String maMonHoc = formController.getResult();
            MonHocDAO mhDAO = new MonHocDAO();
            if (!maMonHoc.trim().equals("")) {
                // Gọi DAO/Service để lưu database
                mhDAO.deleteSubject(maMonHoc);
                loadTableMonHoc();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void clickOnTimKiem(ActionEvent event){
        String timKiem = txtTimKiem.getText().trim();
        if (!timKiem.equals("")) {
            String find = timKiem.toUpperCase();
            MonHoc mh = danhSachMonHoc.get(find);
            tableMonHoc.setItems(FXCollections.observableArrayList(mh));
        } else {
            loadTableMonHoc();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<MonHoc> list = mhDAO.selectSubject(qlk);
        danhSachMonHoc = new HashMap<>();
        for (MonHoc mh : list) {
            danhSachMonHoc.put(mh.getMaMonHoc().trim().toUpperCase(), mh);
        }
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
