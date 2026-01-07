/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import Model.GiangVien;
import Model.KyHoc;
import Model.MonHoc;
import Model.Nganh;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MonHocDAO {

    DatabaseConnect dbConnect = new DatabaseConnect();
// Các chức năng của Môn Học

    public boolean insertNewSubject(MonHoc mh) {
        String sql = "INSERT INTO MonHoc (SubjectID, SubjectName, Credits, DepartmentID) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return false;
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mh.getMaMonHoc().trim());
            pstmt.setString(2, mh.getTenMonHoc().trim());
            pstmt.setInt(3, mh.getSoTinChi());
            pstmt.setString(4, mh.getMaKhoa());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Thêm dữ liệu thành công: " + mh.getTenMonHoc());
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi thêm dữ liệu: " + e.getMessage());
            return false;
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Trong lớp DatabaseConnector
    public boolean deleteSubject(String maMonHoc) {
        String sql = "DELETE FROM MonHoc WHERE SubjectID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return false;
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maMonHoc.trim());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Xóa thành công Khoa có mã: " + maMonHoc + " khỏi Database.");
                return true;
            } else {
                System.out.println("Không tìm thấy Khoa có mã: " + maMonHoc + " trong Database để xóa.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi xóa dữ liệu: " + e.getMessage());
            return false;
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public ArrayList<MonHoc> selectSubject(qlyKhoaDAO qlyKhoa) {
        String sql = "SELECT SubjectID, SubjectName, Credits, DepartmentID FROM MonHoc";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<MonHoc> danhSachMonHoc = new ArrayList<>();
        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return danhSachMonHoc;
            }

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String maMonHoc = rs.getString("SubjectID");
                String tenMonHoc = rs.getString("SubjectName");
                int soTinChi = rs.getInt("Credits");
                String maKhoa = rs.getString("DepartmentID");
                
                MonHoc mh = new MonHoc(maMonHoc, tenMonHoc, soTinChi, maKhoa);
                danhSachMonHoc.add(mh);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi truy vấn danh sách Khoa: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return danhSachMonHoc;
    }
}
