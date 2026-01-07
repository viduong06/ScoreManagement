package Controller;

import Database.ExcelUtil;
import Database.GiangVienDAO;
import Database.KhoaDAO;
import Database.LopDAO;
import Database.qlyGiangVienDAO;
import Database.qlyKhoaDAO;
import Database.qlyMonHocDAO;
import Model.GiangVien;
import Model.Khoa;
import Model.Lop;
import Model.Nganh;
import java.io.IOException;
import java.net.URL;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class qlyLopHocController implements Initializable {

    GiangVienDAO gvDAO = new GiangVienDAO();
    LopDAO lopDAO = new LopDAO();
    qlyKhoaDAO qly = new qlyKhoaDAO();
    qlyGiangVienDAO qly1 = new qlyGiangVienDAO(qly);

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView tableLop;
    @FXML
    private TableColumn<Lop, String> colMaLop;
    @FXML
    private TableColumn colMaKhoa;
    @FXML
    private TableColumn<Lop, String> colTenLop;
    @FXML
    private TableColumn<Lop, String> colTenGiangVien;
    @FXML
    private TableColumn<Lop, String> colSDT;
    @FXML
    private TextField txtTimKiem;

    private HashMap<String, Lop> danhSachLop;

    public void loadTableLop() {
        List<Lop> list = lopDAO.selectClass(qly1);
        tableLop.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    private void clickOnThemLop(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuAdmin/formThemLopFXML.fxml"));
            Parent root = loader.load();

            formThemLopController formController = loader.getController();

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Thêm lớp");
            dialog.showAndWait();  // đợi form đóng

            // Lấy kết quả sau khi dialog đóng
            Lop LopMoi = formController.getResult();
            if (LopMoi != null) {
                String maGV = formController.getTxtMaGiangVien().trim();
                lopDAO.insertNewClass(LopMoi, maGV);
                loadTableLop();
                ExcelUtil.taoFileExcelLop(LopMoi.getMaLop().toLowerCase());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickOnXoaLopHoc(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuAdmin/formXoaLopFXML.fxml"));
            Parent root = loader.load();

            formXoaLopController formController = loader.getController();

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Xoá Lớp");
            dialog.showAndWait();  // đợi form đóng

            // Lấy kết quả sau khi dialog đóng
            String maLop = formController.getResult();
            LopDAO lopDAO = new LopDAO();
            if (!maLop.trim().equals("")) {
                // Gọi DAO/Service để lưu database
                lopDAO.deleteClass(maLop);
                loadTableLop();
                ExcelUtil.xoaFileExcel(maLop);
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
            Lop lop1 = danhSachLop.get(find);
            tableLop.setItems(FXCollections.observableArrayList(lop1));
        } else {
            loadTableLop();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Lop> list = lopDAO.selectClass(qly1);
        danhSachLop = new HashMap<>();
        for (Lop lop : list) {
            danhSachLop.put(lop.getMaLop().trim().toUpperCase(), lop);
        }
        anchorPane.setTranslateY(-270);
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), anchorPane);
        slideIn.setFromY(-270);
        slideIn.setToY(0);
        slideIn.play();
        colMaLop.setCellValueFactory(new PropertyValueFactory<>("maLop"));
        colTenLop.setCellValueFactory(new PropertyValueFactory<>("tenLop"));
        colTenGiangVien.setCellValueFactory(new PropertyValueFactory<>("tenGV"));
        colSDT.setCellValueFactory(new PropertyValueFactory<>("sdtGiangVien"));
        colMaKhoa.setCellValueFactory(new PropertyValueFactory<>("maKhoa"));

        loadTableLop();
    }
}
