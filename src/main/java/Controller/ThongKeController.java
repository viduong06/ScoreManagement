/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.ExcelUtil;
import Database.LopDAO;
import Database.qlyGiangVienDAO;
import Database.qlyKhoaDAO;
import Model.DangNhap;
import Model.DiemThi;
import Model.Lop;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 *
 * @author quochuy
 */
public class ThongKeController implements Initializable {

    LopDAO lopDAO = new LopDAO();
    qlyKhoaDAO qlk = new qlyKhoaDAO();
    qlyGiangVienDAO qlgv = new qlyGiangVienDAO(qlk);
    List<Lop> dsLopQly = lopDAO.selectClassesByTeacherID(DangNhap.getUsername(), qlgv);

    @FXML
    private ComboBox<String> cbMaLop;
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private Label lblTruot;
    @FXML
    private Label lblQua;
    @FXML
    private Label lblTongSo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Lop lop : dsLopQly) {
            cbMaLop.getItems().add(lop.getMaLop());
        }
    }

    @FXML
    public void onSelectClass(ActionEvent event) {
        String maLop = cbMaLop.getValue();
        if (maLop == null || maLop.isBlank()) {
            return;
        }

        ObservableList<DiemThi> data = ExcelUtil.readExcel(maLop);

        if (data.isEmpty()) {
            clearCharts();
            return;
        }

        int truot = 0;  // điểm < 4.0
        int qua = 0;    // điểm >= 4.0
        int[] diemCount = new int[11];

        for (DiemThi dt : data) {
            double diemTK = dt.getDiemTK();

            if (diemTK < 4.0) {
                truot++;
            } else {
                qua++;
            }

            int mucDiem = (int) Math.round(diemTK);
            if (mucDiem >= 0 && mucDiem <= 10) {
                diemCount[mucDiem]++;
            }
        }

        int tongSo = data.size();

        updatePieChart(truot, qua);
        updateBarChart(diemCount);
        updateLabels(truot, qua, tongSo);
    }

    private void updatePieChart(int truot, int qua) {
        pieChart.getData().clear();
        if (qua > 0) {
            pieChart.getData().add(new PieChart.Data("Qua (" + qua + ")", qua));
        }
        if (truot > 0) {
            pieChart.getData().add(new PieChart.Data("Trượt (" + truot + ")", truot));
        }
    }

    private void updateBarChart(int[] diemCount) {
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Số SV");
        for (int i = 0; i <= 10; i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i), diemCount[i]));
        }
        barChart.getData().add(series);
        barChart.setLegendVisible(false);
    }

    private void updateLabels(int truot, int qua, int tongSo) {
        lblQua.setText("Qua: " + qua);
        lblTruot.setText("Trượt: " + truot);
        lblTongSo.setText("Tổng: " + tongSo);
    }

    private void clearCharts() {
        pieChart.getData().clear();
        barChart.getData().clear();
        lblQua.setText("Qua: 0");
        lblTruot.setText("Trượt: 0");
        lblTongSo.setText("Tổng: 0");
    }
}
