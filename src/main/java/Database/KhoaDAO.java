/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import Model.Khoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author quochuy
 */
public class KhoaDAO {
    DatabaseConnect dbConnect = new DatabaseConnect();
// Các chức năng của khoa
    public boolean insertNewDepartment(Khoa khoa) {
        String sql = "INSERT INTO Khoa (DepartmentID, DepartmentName) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnect.openConnection(); 
            if (conn == null) { return false; }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, khoa.getMaKhoa().trim());      
            pstmt.setString(2, khoa.getTenKhoa());              

            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Thêm dữ liệu thành công: " + khoa.getMaKhoa());
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
    public boolean deleteDepartment(String maKhoa) {
    String sql = "DELETE FROM Khoa WHERE DepartmentID = ?"; 
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
        conn = dbConnect.openConnection();
        if (conn == null) { return false; }

        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, maKhoa.trim()); 

        int rowsAffected = pstmt.executeUpdate();
        
        if (rowsAffected > 0) {
            System.out.println("Xóa thành công Khoa có mã: " + maKhoa + " khỏi Database.");
            return true;
        } else {
            System.out.println("Không tìm thấy Khoa có mã: " + maKhoa + " trong Database để xóa.");
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

    public ArrayList<Khoa> selectDepartments() {
    String sql = "SELECT DepartmentID, DepartmentName FROM Khoa"; 
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ArrayList<Khoa> danhSachKhoa = new ArrayList<>(); 
    try {
        conn = dbConnect.openConnection(); 
        if (conn == null) { return danhSachKhoa; } 

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery(); 

        while (rs.next()) {
            String maKhoa = rs.getString("DepartmentID"); 
            String tenKhoa = rs.getString("DepartmentName");
            
            Khoa khoa = new Khoa(maKhoa, tenKhoa); 
            danhSachKhoa.add(khoa); 
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
    return danhSachKhoa;
}
    public boolean updateDepartment(Khoa khoa) {
        String sql = "UPDATE Khoa SET DepartmentName = ? WHERE DepartmentID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnect.openConnection();
            if (conn == null) return false;

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, khoa.getTenKhoa());
            pstmt.setString(2, khoa.getMaKhoa());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi cập nhật khoa: " + e.getMessage());
            return false;
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) {}
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
    }

}
