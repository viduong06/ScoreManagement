/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import Model.Lop;
import Model.SinhVien;
import java.util.ArrayList;

public class qlySinhVienDAO {
    private ArrayList<SinhVien> danhSinhVien;
    private SinhVienDAO connector;
    
    public qlySinhVienDAO(qlyKhoaDAO qlk, qlyNganhDAO qln) {
    this.connector = new SinhVienDAO();
    this.danhSinhVien = new ArrayList<>();  
    this.loadDataFromDatabase(); 
    }
    
    public void loadDataFromDatabase() {
        this.danhSinhVien = connector.selectStudent(); 
        System.out.println("Đã tải thành công " + this.danhSinhVien.size() + " Khoa từ CSDL.");
    }
        
//    public boolean them(SinhVien sv) {
//        if (this.tim(sv.getMssv()) != null) {
//            return false; 
//        } else {
//            boolean dbSuccess = connector.insertNewStudent(sv, sv.getKhoa(), sv.getNganh());
//            if (dbSuccess) {
//                this.danhSinhVien.add(sv); 
//                return true;
//            } else {
//                return false; 
//            }
//        }
//    }
//    
    public SinhVien tim(String maSinhVien) {
        for(SinhVien find : this.danhSinhVien) {
            if (find.getMssv().equals(maSinhVien)) {
                return find;
            }
        }
        return null;
    }
    
    public boolean xoa(String maSinhVien) {
        SinhVien svCanXoa = this.tim(maSinhVien);
        if (svCanXoa == null) {
        return false;
    }
    
    boolean dbSuccess = connector.deleteStudent(maSinhVien);
    
    if (dbSuccess) {
        this.danhSinhVien.remove(svCanXoa); 
        return true;
    } else {
        return false;
       }
    }
    
//    public void loadClassesForStudent(SinhVien sv, qlyMonHocDAO qlmh, qlyKyHocDAO qlkh, qlyGiangVienDAO qlgv) {
//    ArrayList<Lop> danhSachLopTuDB = 
//        connector.selectClassesByStudentID(sv.getMSSV(), qlmh, qlkh, qlgv);
//    for (Lop lop : danhSachLopTuDB) {
//        sv.addLopHoc(lop);
//    }
//}
}
