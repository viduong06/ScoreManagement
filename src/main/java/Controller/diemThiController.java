/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.ExcelUtil;
import Database.KhoaDAO;
import Database.LopDAO;
import Database.SinhVienDAO;
import Database.qlyGiangVienDAO;
import Database.qlyKhoaDAO;
import Model.DangNhap;
import Model.DiemThi;
import Model.Lop;
import Model.MonHoc;
import Model.SinhVien;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class diemThiController implements Initializable {

    SinhVienDAO svDAO = new SinhVienDAO();
    List<SinhVien> list;
    LopDAO lopDAO = new LopDAO();
    qlyKhoaDAO qlk = new qlyKhoaDAO();
    qlyGiangVienDAO qlgv = new qlyGiangVienDAO(qlk);
    List<Lop> dsLopQly = lopDAO.selectClassesByTeacherID(DangNhap.getUsername(), qlgv);
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView tableDiemThi;
    @FXML
    private TextField txtTimKiem;
    @FXML
    private TableColumn colMSSV;
    @FXML
    private TableColumn colTen;
    @FXML
    private TableColumn colDGK1;
    @FXML
    private TableColumn colDGK2;
    @FXML
    private TableColumn colDCK;
    @FXML
    private TableColumn colDTK;
    @FXML
    private ComboBox<String> cbMaLop;
    
    private HashMap<String, DiemThi> danhSachDiemThi = new HashMap();
    
    private void loadTableDiemThi() {
    String maLop = cbMaLop.getValue();
    if (maLop == null || maLop.isBlank()) {
        return;
    }
    ObservableList<DiemThi> data = ExcelUtil.readExcel(maLop);
    danhSachDiemThi.clear();
    for (DiemThi dt : data) {
        danhSachDiemThi.put(dt.getMaSV().trim().toUpperCase(),dt
        );
    }

    tableDiemThi.setVisible(true);
    tableDiemThi.setItems(data);
}

     
    @FXML
    public void chooseClass(ActionEvent event) {
        loadTableDiemThi();
    }
    @FXML
    private void clickOnNhapDiem(ActionEvent event) {
        try {FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuGiangVien/formNhapDiemFXML.fxml"));
            Parent root = loader.load();

            formNhapDiemController formController = loader.getController();

            Stage dialog = new Stage();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Thêm sinh viên");
            dialog.showAndWait();  // đợi form đóng

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickRefresh() {
        loadTableDiemThi();
    }

    @FXML
    public void clickOnTimKiem(ActionEvent event) {
        String timKiem = txtTimKiem.getText().trim();
        if (!timKiem.equals("")) {
            String find = timKiem.toUpperCase();
            DiemThi dt = danhSachDiemThi.get(find);
            tableDiemThi.setItems(FXCollections.observableArrayList(dt));
        } else {
            loadTableDiemThi();
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
        colMSSV.setCellValueFactory(new PropertyValueFactory<>("maSV"));
        colTen.setCellValueFactory(new PropertyValueFactory<>("tenSV"));
        colDGK1.setCellValueFactory(new PropertyValueFactory<>("diemGK1"));
        colDGK2.setCellValueFactory(new PropertyValueFactory<>("diemGK2"));
        colDCK.setCellValueFactory(new PropertyValueFactory<>("diemCK"));
        colDTK.setCellValueFactory(new PropertyValueFactory<>("diemTK"));
    }
}