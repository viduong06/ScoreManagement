package Model;

import java.util.ArrayList;
import java.util.List;

public class GiangVien {

    private String maGiangVien;
    private String tenGiangVien;
    private String email;
    private String sdt;
    private String maKhoa;
    private ArrayList<Lop> dsLopHoc;

    public GiangVien() {
    }

    ;

    public GiangVien(String maGiangVien, String tenGiangVien, String email, String sdt, String maKhoa) {
        this.maGiangVien = maGiangVien;
        this.tenGiangVien = tenGiangVien;
        this.email = email;
        this.sdt = sdt;
        this.maKhoa = maKhoa;
        dsLopHoc = new ArrayList();
    }

    public static boolean tonTaiMaGiangVien(List<GiangVien> list, String maGV) {
        for (GiangVien gv : list) {
            if (gv.getMaGiangVien().trim().equalsIgnoreCase(maGV)) {
                return true;
            }
        }
        return false;
    }

    public String getMaGiangVien() {
        return maGiangVien.trim();
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getTenGiangVien() {
        return tenGiangVien.trim();
    }

    public String getEmail() {
        return email.trim();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSDT() {
        return sdt.trim();
    }

    public void setSDT(String SDT) {
        this.sdt = SDT;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }


    public void addLopHoc(Lop lop) {
        if (!this.dsLopHoc.contains(lop)) {
            this.dsLopHoc.add(lop);
        }
    }

    public ArrayList<Lop> getDsLopHoc() {
        return dsLopHoc;
    }
}
