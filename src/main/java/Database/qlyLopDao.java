/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import Model.Lop;
import Model.SinhVien;
import java.util.ArrayList;

public class qlyLopDao {
    private ArrayList<Lop> danhSachLop;
    private LopDAO connector;
    
    public qlyLopDao(qlyMonHocDAO qlmh, qlyKyHocDAO qlkh, qlyGiangVienDAO qlgv) {
    this.connector = new LopDAO();
    this.danhSachLop = new ArrayList<>();  
    this.loadDataFromDatabase(qlgv); 
    }
    
    public void loadDataFromDatabase(qlyGiangVienDAO qlgv) {
        this.danhSachLop = connector.selectClass(qlgv); 
        System.out.println("Đã tải thành công " + this.danhSachLop.size() + " Khoa từ CSDL.");
    }
        
    
    public Lop tim(String maLop) {
        for(Lop find : this.danhSachLop) {
            if (find.getMaLop().equals(maLop)) {
                return find;
            }
        }
        return null;
    }
    
    public boolean xoa(String maLop) {
        Lop lopCanXoa = this.tim(maLop);
        if (lopCanXoa == null) {
        return false;
    }
    
    boolean dbSuccess = connector.deleteClass(maLop);
    
    if (dbSuccess) {
        this.danhSachLop.remove(lopCanXoa); 
        return true;
    } else {
        return false;
       }
    }
//    public void loadStudentsForClass(Lop lop, qlyKhoaDAO qlk, qlyNganhDAO qln) {
//    ArrayList<SinhVien> danhSachTuDB = 
//        connector.selectStudentsByClassID(lop.getMaLop(), qlk, qln);
//
//    for (SinhVien sv : danhSachTuDB) {
//        lop.addSinhVien(sv); 
//    }
}

