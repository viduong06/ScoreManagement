
package Database;

import Model.Nganh;
import Model.QuanLyKhoa;
import java.util.ArrayList;

public class qlyNganhDAO {
    private ArrayList<Nganh> danhSachNganh;
    private NganhDAO connector;
    
    public qlyNganhDAO() {
    this.connector = new NganhDAO();
    this.danhSachNganh = new ArrayList<>();  
    }
    
    public void loadDataFromDatabase() {
        qlyKhoaDAO qlyKhoa = new qlyKhoaDAO();
        this.danhSachNganh = connector.selectMajor(qlyKhoa); 
        System.out.println("Đã tải thành công " + this.danhSachNganh.size() + " Khoa từ CSDL.");
    }

    
    public Nganh tim(String maNganh) {
        for(Nganh find : this.danhSachNganh) {
            if (find.getMaNganh().equals(maNganh)) {
                return find;
            }
        }
        return null;
    }
    
    public boolean xoa(String maNganh) {
        Nganh nganhCanXoa = this.tim(maNganh);
        if (nganhCanXoa == null) {
        return false;
    }
    
    boolean dbSuccess = connector.deleteMajor(maNganh);
    
    if (dbSuccess) {
        this.danhSachNganh.remove(nganhCanXoa); 
        return true;
    } else {
        return false;
       }
    }
    
//    public boolean sua(Nganh nganhMoi) {
//        Nganh nganhCu = this.tim(nganhMoi.getMaNganh());
//        if (nganhCu == null) return false;
//
//        boolean dbSuccess = connector.updateMajor(nganhMoi);
//        if (!dbSuccess) return false;
//
//        nganhCu.setTenNganh(nganhMoi.getTenNganh());
//
//        return true;
//    }
}
