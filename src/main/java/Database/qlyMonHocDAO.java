/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import Model.MonHoc;
import java.util.ArrayList;

public class qlyMonHocDAO {
    private MonHocDAO connector;
    private ArrayList<MonHoc> danhSachMonHoc;
    public qlyMonHocDAO(qlyKhoaDAO qlyKhoa) {
    danhSachMonHoc = new ArrayList<>();
    this.connector = new MonHocDAO();
    this.loadDataFromDatabase(qlyKhoa); 
    }
    
    public void loadDataFromDatabase(qlyKhoaDAO qlyKhoa) {
        this.danhSachMonHoc = connector.selectSubject(qlyKhoa); 
        System.out.println("Đã tải thành công " + this.danhSachMonHoc.size() + " Khoa từ CSDL.");
    }
        
    
    public MonHoc tim(String maMonHoc) {
        for(MonHoc find : this.danhSachMonHoc) {
            if (find.getMaMonHoc().equals(maMonHoc)) {
                return find;
            }
        }
        return null;
    }
    
//    public boolean xoa(String maMonHoc) {
//        MonHoc mhCanXoa = this.tim(maMonHoc);
//        if (mhCanXoa == null) {
//        return false;
//    }
    
//    boolean dbSuccess = connector.deleteSubject(maMonHoc);
//    
//    if (dbSuccess) {
//        this.danhSachMonHoc.remove(mhCanXoa); 
//        return true;
//    } else {
//        return false;
//       }
//    }
    
}
