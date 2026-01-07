//
//package Model;
//
//import java.util.ArrayList;
//
//public class QuanLyNganh {
//    private ArrayList<Nganh> danhSachNganh;
//    private DatabaseConnector connector;
//    
//    public QuanLyNganh(QuanLyKhoa qlKhoa) {
//    this.connector = new DatabaseConnector();
//    this.danhSachNganh = new ArrayList<>();  
//    this.loadDataFromDatabase(qlKhoa); 
//    }
//    
//    public void loadDataFromDatabase(QuanLyKhoa qlKhoa) {
//        this.danhSachNganh = connector.selectMajor(qlKhoa); 
//        System.out.println("Đã tải thành công " + this.danhSachNganh.size() + " Khoa từ CSDL.");
//    }
//        
//    public boolean them(Nganh nganh) {
//        if (this.tim(nganh.getMaNganh()) != null) {
//            return false; 
//        } else {
//            boolean dbSuccess = connector.insertNewMajor(nganh, nganh.getKhoa());
//            if (dbSuccess) {
//                this.danhSachNganh.add(nganh); 
//                return true;
//            } else {
//                return false; 
//            }
//        }
//    }
//    
//    public Nganh tim(String maNganh) {
//        for(Nganh find : this.danhSachNganh) {
//            if (find.getMaNganh().equals(maNganh)) {
//                return find;
//            }
//        }
//        return null;
//    }
//    
//    public boolean xoa(String maNganh) {
//        Nganh nganhCanXoa = this.tim(maNganh);
//        if (nganhCanXoa == null) {
//        return false;
//    }
//    
//    boolean dbSuccess = connector.deleteDepartment(maNganh);
//    
//    if (dbSuccess) {
//        this.danhSachNganh.remove(nganhCanXoa); 
//        return true;
//    } else {
//        return false;
//       }
//    }
//}
