/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author quochuy
 */
public class DiemThi {

    private String maSV;
    private String tenSV;
    private double diemGK1;
    private double diemGK2;
    private double diemCK;
    private double diemTK;

    public double getDiemTK() {
        return diemTK;
    }

    public void setDiemTK(double diemTK) {
        this.diemTK = diemTK;
    }

    public DiemThi(String maSV, String tenSV, double diemGK1, double diemGK2, double diemCK, double diemTK) {
        this.maSV = maSV;
        this.tenSV = tenSV;
        this.diemGK1 = diemGK1;
        this.diemGK2 = diemGK2;
        this.diemCK = diemCK;
        this.diemTK = diemTK;
    }

    public double tinhDiemTong() {
        return diemGK1 * 0.25 + diemGK2 * 0.25 + diemCK * 0.5;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getTenSV() {
        return tenSV;
    }

    public void setTenSV(String tenSV) {
        this.tenSV = tenSV;
    }

    public double getDiemGK1() {
        return diemGK1;
    }

    public void setDiemGK1(double diemGK1) {
        this.diemGK1 = diemGK1;
    }

    public double getDiemGK2() {
        return diemGK2;
    }

    public void setDiemGK2(double diemGK2) {
        this.diemGK2 = diemGK2;
    }

    public double getDiemCK() {
        return diemCK;
    }

    public void setDiemCK(double diemCK) {
        this.diemCK = diemCK;
    }

}
