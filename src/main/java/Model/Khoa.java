package Model;

import java.util.ArrayList;
import java.util.List;

public class Khoa {
    private String maKhoa;
    private String tenKhoa;
    private ArrayList<Nganh> dsNganh;
    private ArrayList<SinhVien> dsSinhVien;
    public Khoa(){
        maKhoa = "";
        tenKhoa = "";
        dsNganh = new ArrayList<>();
        dsSinhVien = new ArrayList<>();
    }
    public Khoa(String maKhoa, String tenKhoa) { 
        this.maKhoa = maKhoa;
        this.tenKhoa = tenKhoa;
        this.dsNganh = new ArrayList<>();
        this.dsSinhVien = new ArrayList<>();
    }
    
    public static boolean tonTaiMaKhoa(List<Khoa> list, String ma) {
    for (Khoa k : list) {
        if (k.getMaKhoa().trim().equalsIgnoreCase(ma)) {
            return true;   
        }
    }
    return false;      
    }

    public void addNganh(Nganh nganh) {
        this.dsNganh.add(nganh);
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }
    
    public String getMaKhoa(){
        return maKhoa;
    }
    
    public String getTenKhoa(){
        return tenKhoa;
    }

    public ArrayList<Nganh> getDsNganh() {
        return dsNganh;
    }
    
    public void setTenKhoa(String tenKhoa) {
    this.tenKhoa = tenKhoa;
    }
    
    public void addSinhVien(SinhVien sv) {
        this.dsSinhVien.add(sv);
    }
    
    public ArrayList<SinhVien> getDsSinhVien() {
        return dsSinhVien;
    }
}