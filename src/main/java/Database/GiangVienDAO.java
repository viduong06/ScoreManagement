
package Database;
import Model.GiangVien;
import Model.Khoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GiangVienDAO {
    DatabaseConnect dbConnect = new DatabaseConnect();
// Các chức năng của GiangVien
    public boolean insertNewTeacher(GiangVien gv, String maKhoa) {
        String sql = "INSERT INTO GiangVien (TeacherID, TeacherName, DepartmentID, Email, Phone) VALUES (?, ?, ?, ?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnect.openConnection(); 
            if (conn == null) { return false; }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gv.getMaGiangVien().trim());      
            pstmt.setString(2, gv.getTenGiangVien().trim());
            pstmt.setString(3, maKhoa);
            pstmt.setString(4, gv.getEmail().trim());
            pstmt.setString(5, gv.getSDT().trim());

            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Thêm dữ liệu thành công: " + gv.getTenGiangVien());
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi thêm dữ liệu: " + e.getMessage());
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    // Trong lớp DatabaseConnector
    public boolean deleteTeacher(String maGiangVien) {
    String sql = "DELETE FROM GiangVien WHERE TeacherID = ?"; 
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
        conn = dbConnect.openConnection();
        if (conn == null) { return false; }

        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, maGiangVien.trim()); 

        int rowsAffected = pstmt.executeUpdate();
        
        if (rowsAffected > 0) {
            System.out.println("Xóa thành công Khoa có mã: " + maGiangVien + " khỏi Database.");
            return true;
        } else {
            System.out.println("Không tìm thấy Khoa có mã: " + maGiangVien + " trong Database để xóa.");
            return false;
        }

    } catch (SQLException e) {
        System.err.println("Lỗi SQL khi xóa dữ liệu: " + e.getMessage());
        return false;
    } finally {
        try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

    public ArrayList<GiangVien> selectTeacher(qlyKhoaDAO qlKhoa) {
    String sql = "SELECT TeacherID, TeacherName, DepartmentID, Email, Phone FROM GiangVien"; 
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ArrayList<GiangVien> danhSachGiangVien = new ArrayList<>(); 
    try {
        conn = dbConnect.openConnection(); 
        if (conn == null) { return danhSachGiangVien; } 

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery(); 

        while (rs.next()) {
            String maGiangVien = rs.getString("TeacherID");
            String tenGiangVien = rs.getString("TeacherName");
            String maKhoaFK = rs.getString("DepartmentID");
            String email = rs.getString("Email");
            String sdt = rs.getString("Phone");
            
            Khoa khoa = qlKhoa.tim(maKhoaFK);
            GiangVien gv = new GiangVien(maGiangVien, tenGiangVien, email, sdt, khoa.getMaKhoa());
            danhSachGiangVien.add(gv);
        }

    } catch (SQLException e) {
        System.err.println("Lỗi SQL khi truy vấn danh sách Khoa: " + e.getMessage());
        return new ArrayList<>(); 
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    return danhSachGiangVien;
}
    // Trong lớp GiangVienDAO

public ArrayList<GiangVien> selectTeachersByDepartment(String maKhoaTimKiem, qlyKhoaDAO qlKhoa) {
    
    String sql = "SELECT TeacherID, TeacherName, DepartmentID, Email, Phone FROM GiangVien WHERE DepartmentID = ?"; 
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ArrayList<GiangVien> danhSachGiangVienTheoKhoa = new ArrayList<>();
    
    try {
        conn = dbConnect.openConnection();
        if (conn == null) { return danhSachGiangVienTheoKhoa; }

        pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, maKhoaTimKiem.trim()); 
        
        rs = pstmt.executeQuery();

        while (rs.next()) {
            String maGiangVien = rs.getString("TeacherID");
            String tenGiangVien = rs.getString("TeacherName");
            String maKhoaFK = rs.getString("DepartmentID");
            String email = rs.getString("Email");
            String sdt = rs.getString("Phone");
            
            Khoa khoa = qlKhoa.tim(maKhoaFK);
            
            GiangVien gv = new GiangVien(maGiangVien, tenGiangVien, email, sdt, khoa.getMaKhoa());
            danhSachGiangVienTheoKhoa.add(gv);
        }

    } catch (SQLException e) {
        System.err.println("Lỗi SQL khi truy vấn Giảng Viên theo Khoa: " + e.getMessage());
        return new ArrayList<>();
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    return danhSachGiangVienTheoKhoa;
}
    
}
