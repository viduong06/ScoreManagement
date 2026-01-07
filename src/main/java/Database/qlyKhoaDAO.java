
package Database;

import Model.Khoa;
import java.util.ArrayList;

public class qlyKhoaDAO {
    private ArrayList<Khoa> danhSachKhoa;
    private KhoaDAO connector;
    
    public qlyKhoaDAO() {
        this.connector = new KhoaDAO();
        this.danhSachKhoa = new ArrayList<>();
        this.loadDataFromDatabase(); 
    }

    public void loadDataFromDatabase() {
        this.danhSachKhoa = connector.selectDepartments(); 
    }
        
    public boolean them(Khoa khoa) {
        if (this.tim(khoa.getMaKhoa()) != null) {
            return false;
        } else {
        boolean success = connector.insertNewDepartment(khoa);
        if (success) {
            this.danhSachKhoa.add(khoa);
            return true;
        } else {
            return false;
        }
        }
    }
    
    public Khoa tim(String maKhoa) {
        for(Khoa find : this.danhSachKhoa) {
            if (find.getMaKhoa().equals(maKhoa)) {
                return find;
            }
        }
        return null;
    }
    
    public boolean xoa(String maKhoa) {
        Khoa khoaCanXoa = this.tim(maKhoa);
        if (khoaCanXoa == null) {
        return false;
    }
    
    boolean dbSuccess = connector.deleteDepartment(maKhoa);
    
    if (dbSuccess) {
        this.danhSachKhoa.remove(khoaCanXoa); 
        return true;
    } else {
        return false;
       }
    }
    
    public boolean sua(Khoa khoa) {
        Khoa old = this.tim(khoa.getMaKhoa());
        if (old == null) return false;

        boolean dbSuccess = connector.updateDepartment(khoa);

        if (dbSuccess) {
            old.setTenKhoa(khoa.getTenKhoa());
            return true;
        } else {
            return false;
        }
    }

}
