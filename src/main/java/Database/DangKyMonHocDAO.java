/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import Model.MonHoc;
import Model.SinhVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class DangKyMonHocDAO {

    DatabaseConnect dbConnect = new DatabaseConnect();

    public boolean insertStudentEnrollment(String maSinhVien, String maMonHoc) {
        String sql = "INSERT INTO DangKyMonHoc (StudentID, SubjectID) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return false;
            }

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, maSinhVien.trim());
            pstmt.setString(2, maMonHoc.trim());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Đăng ký thành công: Sinh viên " + maSinhVien + " vào môn " + maMonHoc);
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi đăng ký lớp: " + e.getMessage());
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

    public ArrayList<SinhVien> selectStudentsBySubjectID(String maMonHoc) {
        ArrayList<SinhVien> danhSachSinhVien = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT SV.StudentID, SV.StudentName, SV.[DateOfBirth], SV.HomeroomClass "
                + "FROM SinhVien SV "
                + "INNER JOIN DangKyMonHoc DK ON SV.StudentID = DK.StudentID "
                + "WHERE DK.SubjectID = ?";

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return danhSachSinhVien;
            }

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, maMonHoc.trim());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String maSinhVien = rs.getString("StudentID");
                String tenSinhVien = rs.getString("StudentName");
                LocalDate ngaySinh = rs.getDate("DateOfBirth").toLocalDate(); // Lấy Date
                String lopChuQuan = rs.getString("HomeroomClass");

                SinhVien sv = new SinhVien(maSinhVien, tenSinhVien, ngaySinh, lopChuQuan);

                danhSachSinhVien.add(sv);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi truy vấn sinh viên theo lớp: " + e.getMessage());
        } finally {
            // Đóng tài nguyên
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
        return danhSachSinhVien;
    }

    public ArrayList<MonHoc> selectSubjectIDByStudentID(String maSinhVien) {
        ArrayList<MonHoc> danhSachMonHoc = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT SV.SubjectID, SV.SubjectName, SV.Credits, SV.DepartmentID "
                + "FROM MonHoc SV "
                + "INNER JOIN DangKyMonHoc DK ON SV.SubjectID = DK.SubjectID "
                + "WHERE DK.StudentID = ?";

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return danhSachMonHoc;
            }

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, maSinhVien.trim());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String maMonHoc = rs.getString("SubjectID");
                String tenMonHoc = rs.getString("SubjectName");
                int tinChi = rs.getInt("Credits");
                String maKhoa = rs.getString("DepartmentID");
                MonHoc mh = new MonHoc(maMonHoc, tenMonHoc, tinChi, maKhoa);

                danhSachMonHoc.add(mh);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi truy vấn sinh viên theo lớp: " + e.getMessage());
        } finally {
            // Đóng tài nguyên
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

    public boolean deleteStudentEnrollment(String maSinhVien, String maMonHoc) {
        String sql = "DELETE FROM DangKyMonHoc WHERE StudentID = ? AND SubjectID = ?";

        try (Connection conn = dbConnect.openConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maSinhVien.trim());
            pstmt.setString(2, maMonHoc.trim());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi hủy đăng ký: " + e.getMessage());
            return false;
        }
    }
}
