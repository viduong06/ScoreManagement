/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import Model.GiangVien;
import Model.QuanLyKhoa;
import java.util.ArrayList;


public class qlyGiangVienDAO {
    private ArrayList<GiangVien> danhSachGiangVien;
    private GiangVienDAO connector;
    
    public qlyGiangVienDAO(qlyKhoaDAO qlKhoa) {
    this.connector = new GiangVienDAO();
    this.danhSachGiangVien = new ArrayList<>();  
    this.loadDataFromDatabase(qlKhoa); 
    }
    
    public void loadDataFromDatabase(qlyKhoaDAO qlKhoa) {
        this.danhSachGiangVien = connector.selectTeacher(qlKhoa); 
        System.out.println("Đã tải thành công " + this.danhSachGiangVien.size() + " giangvien từ CSDL.");
    }

    
    public GiangVien tim(String maGiangVien) {
        for(GiangVien find : this.danhSachGiangVien) {
            if (find.getMaGiangVien().equals(maGiangVien.trim())) {
                return find;
            }
        }
        return null;
    }
    
    public boolean xoa(String maGiangVien) {
        GiangVien gvCanXoa = this.tim(maGiangVien);
        if (gvCanXoa == null) {
        return false;
    }
    
    boolean dbSuccess = connector.deleteTeacher(maGiangVien);
    
    if (dbSuccess) {
        this.danhSachGiangVien.remove(gvCanXoa); 
        return true;
    } else {
        return false;
       }
    }
    
    public ArrayList<GiangVien> locGiangVienTheoKhoa(String maKhoa, qlyKhoaDAO qlKhoa) {
    return connector.selectTeachersByDepartment(maKhoa, qlKhoa);
}
    
}
