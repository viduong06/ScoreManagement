package Database;

import Database.DatabaseConnect;
import Database.qlyKhoaDAO;
import Model.SinhVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class DangKyLopHocDAO {

    DatabaseConnect dbConnect = new DatabaseConnect();

    public boolean insertStudentEnrollment(String maSinhVien, String maLopHoc) {
        String sql = "INSERT INTO DangKy (StudentID, ClassID) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return false;
            }

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, maSinhVien.trim());
            pstmt.setString(2, maLopHoc.trim());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Đăng ký thành công: Sinh viên " + maSinhVien + " vào lớp " + maLopHoc);
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

    public ArrayList<SinhVien> selectStudentsByClassID(String maLopHoc) {
        ArrayList<SinhVien> danhSachSinhVien = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT SV.StudentID, SV.StudentName, SV.[DateOfBirth], SV.HomeroomClass "
                + "FROM SinhVien SV "
                + "INNER JOIN DangKy DK ON SV.StudentID = DK.StudentID "
                + "WHERE DK.ClassID = ? ORDER BY STT_Nhap ASC";

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return danhSachSinhVien;
            }

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, maLopHoc.trim());

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

}
