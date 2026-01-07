/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import Model.KyHoc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class KyHocDAO {
    DatabaseConnect dbConnect = new DatabaseConnect();
// Các chức năng của Kỳ học
    public boolean insertNewSemester(KyHoc kh) {
        String sql = "INSERT INTO KyHoc (SemesterID) VALUES (?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnect.openConnection(); 
            if (conn == null) { return false; }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, kh.getTenKy());

            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Thêm dữ liệu thành công: " + kh.getTenKy());
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
    public boolean deleteSemester(String maKy) {
    String sql = "DELETE FROM HocKy WHERE SemesterID = ?"; 
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
        conn = dbConnect.openConnection();
        if (conn == null) { return false; }

        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, maKy.trim()); 

        int rowsAffected = pstmt.executeUpdate();
        
        if (rowsAffected > 0) {
            System.out.println("Xóa thành công Khoa có mã: " + maKy + " khỏi Database.");
            return true;
        } else {
            System.out.println("Không tìm thấy Khoa có mã: " + maKy + " trong Database để xóa.");
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

    public ArrayList<KyHoc> selectSemester() {
    String sql = "SELECT SemesterID FROM HocKy"; 
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ArrayList<KyHoc> danhSachHocKy = new ArrayList<>(); 
    try {
        conn = dbConnect.openConnection(); 
        if (conn == null) { return danhSachHocKy; } 

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery(); 

        while (rs.next()) {
            String maHocKy = rs.getString("SemesterID");
            KyHoc kh = new KyHoc(maHocKy);
            danhSachHocKy.add(kh);
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
    return danhSachHocKy;
}
}
