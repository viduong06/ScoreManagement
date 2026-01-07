/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import Model.KyHoc;
import Model.MonHoc;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class qlyKyHocDAO {
    private ArrayList<KyHoc> danhSachKyHoc;
    private KyHocDAO connector;
    
    public qlyKyHocDAO() {
    this.connector = new KyHocDAO();
    this.danhSachKyHoc = new ArrayList<>(); 
    this.loadDataFromDatabase(); 
    }
    
    public void loadDataFromDatabase() {
        this.danhSachKyHoc = connector.selectSemester();
        System.out.println("Đã tải thành công " + this.danhSachKyHoc.size() + " Khoa từ CSDL.");
    }
        
    public boolean them(KyHoc kh) {
        if (this.tim(kh.getTenKy()) != null) {
            return false; 
        } else {
            boolean dbSuccess = connector.insertNewSemester(kh);
            if (dbSuccess) {
                this.danhSachKyHoc.add(kh); 
                return true;
            } else {
                return false; 
            }
        }
    }
    
    public KyHoc tim(String maKyHoc) {
        for(KyHoc find : this.danhSachKyHoc) {
            if (find.getTenKy().equals(maKyHoc)) {
                return find;
            }
        }
        return null;
    }
    
    public boolean xoa(String maKyHoc) {
        KyHoc khCanXoa = this.tim(maKyHoc);
        if (khCanXoa == null) {
        return false;
    }
    
    boolean dbSuccess = connector.deleteSemester(maKyHoc);
    
    if (dbSuccess) {
        this.danhSachKyHoc.remove(khCanXoa); 
        return true;
    } else {
        return false;
       }
    }
    
    public ArrayList<MonHoc> getDsMonHocTrongKy(String tenKyHoc) {
    KyHoc kyHoc = this.tim(tenKyHoc); 
    
    if (kyHoc != null) {
        return kyHoc.getDsMonHoc(); 
    }
    
    return new ArrayList<>();
}
}
