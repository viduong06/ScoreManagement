package Model;

import java.util.ArrayList;
import java.util.List;

public class Nganh {

    private String maNganh;
    private String tenNganh;
    private String tenKhoa;
    private ArrayList<MonHoc> dsMon;
    private ArrayList<SinhVien> dsSinhVien;

    public Nganh(String maNganh, String tenNganh, String tenKhoa) {
        this.maNganh = maNganh;
        this.tenNganh = tenNganh;
        this.tenKhoa = tenKhoa;
        this.dsMon = new ArrayList<>();
        this.dsSinhVien = new ArrayList<>();

    }

    public static boolean tonTaiMaNganh(List<Nganh> list, String ma) {
        for (Nganh k : list) {
            if (k.getMaNganh().trim().equalsIgnoreCase(ma)) {
                return true;
            }
        }
        return false;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }



    public void addMonHoc(MonHoc mh) {
        if (!this.dsMon.contains(mh)) {
            this.dsMon.add(mh);
        }
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }

    public String getMaNganh() {
        return maNganh.trim();
    }

    public String getTenNganh() {
        return tenNganh.trim();
    }

    public ArrayList<MonHoc> getDsMonHoc() {
        return dsMon;
    }

    public void addSinhVien(SinhVien sv) {
        this.dsSinhVien.add(sv);
    }

    public ArrayList<SinhVien> getDsSinhVien() {
        return dsSinhVien;
    }
}
