package Model;

import java.util.ArrayList;

public class MonHoc {

    private String maMonHoc;
    private String tenMonHoc;
    private int soTinChi;
    private String maKhoa;

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }
    private ArrayList<Lop> dsLopHoc;

    public MonHoc(String maMonHoc, String tenMonHoc, int soTinChi, String maKhoa) {
        this.maMonHoc = maMonHoc;
        this.tenMonHoc = tenMonHoc;
        this.soTinChi = soTinChi;
        this.maKhoa = maKhoa;
        this.dsLopHoc = new ArrayList<Lop>();
    }

    public String getMaMonHoc() {
        return maMonHoc;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public int getSoTinChi() {
        return soTinChi;
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
