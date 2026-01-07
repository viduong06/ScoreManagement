
package Model;

import java.util.ArrayList;

public class KyHoc {
    private String tenKy;
    private ArrayList<MonHoc> dsMonHoc;
    private ArrayList<Lop> dsLopHoc;
    
    public KyHoc(String tenKy){
    this.tenKy = tenKy;
    dsMonHoc = new ArrayList();
    dsLopHoc = new ArrayList();
    }
    
    public String getTenKy(){
    return tenKy;
    }
    
    public void addMonHoc(MonHoc mh) {
        if (!this.dsMonHoc.contains(mh)) {
            this.dsMonHoc.add(mh);
        }
    }
    
    public void addLopHoc(Lop lop) {
        if (!this.dsLopHoc.contains(lop)) {
            this.dsLopHoc.add(lop);
        }
    }
    
    public ArrayList<MonHoc> getDsMonHoc() {
        return dsMonHoc;
    }
    
    public ArrayList<Lop> getDsLopHoc() {
        return dsLopHoc;
    }
}
