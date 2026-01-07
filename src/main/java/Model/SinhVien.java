package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SinhVien {

    private String mssv;
    private String ten;
    private LocalDate ngaySinh;
    private String lopChuQuan;

    public SinhVien(String mssv, String ten, LocalDate ngaySinh, String lopChuQuan) {
        this.mssv = mssv;
        this.ten = ten;
        this.ngaySinh = ngaySinh;
        this.lopChuQuan = lopChuQuan;
    }
public static boolean tonTaiMSSV(List<SinhVien> list, String ma) {
    for (SinhVien sv : list) {
        if (sv.getMssv().trim().equals(ma)) {
            return true;   
        }
    }
    return false;      
    }
    public String getLopChuQuan() {
        return lopChuQuan;
    }

    public void setLopChuQuan(String lopChuQuan) {
        this.lopChuQuan = lopChuQuan;
    }

    public SinhVien() {
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

}
