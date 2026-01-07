package Model;

import java.util.ArrayList;
import java.util.List;

public class Lop {

    private String maLop;
    private String tenLop;
    private String sdtGiangVien;
    private String tenGV;
    private ArrayList<SinhVien> dsSinhVien;
    public Lop(String maLop, String tenLop, String tenGV, String SDT) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.sdtGiangVien = SDT;
        this.tenGV = tenGV;
    }

    public static boolean tonTaiMaLop(List<Lop> list, String ma) {
        for (Lop k : list) {
            if (k.getMaLop().trim().equalsIgnoreCase(ma)) {
                return true;
            }
        }
        return false;
    }

    public String getMaLop() {
        return maLop.trim();
    }

    public String getTenLop() {
        return tenLop.trim();
    }

    public String getSdtGiangVien() {
        return sdtGiangVien.trim();
    }

    public String getTenGV() {
        return tenGV;
    }

    public void addSinhVien(SinhVien sv) {
        this.dsSinhVien.add(sv);
    }

    public ArrayList<SinhVien> getDsSinhVien() {
        return dsSinhVien;
    }

}
