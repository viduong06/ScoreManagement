/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import Model.GiangVien;
import Model.Khoa;
import Model.KyHoc;
import Model.Lop;
import Model.MonHoc;
import Model.Nganh;
import Model.SinhVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class SinhVienDAO {

    DatabaseConnect dbConnect = new DatabaseConnect();
// Các chức năng của Lớp học

    public boolean insertNewStudent(SinhVien sv) {
        String sql = "INSERT INTO SinhVien (StudentID, StudentName, DateOfBirth, HomeroomClass) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return false;
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sv.getMssv().trim());
            pstmt.setString(2, sv.getTen().trim());
            pstmt.setDate(3, java.sql.Date.valueOf(sv.getNgaySinh()));
            pstmt.setString(4, sv.getLopChuQuan());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Thêm dữ liệu thành công: " + sv.getTen());
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
    public boolean deleteStudent(String maSinhVien) {
        String sql = "DELETE FROM SinhVien WHERE StudentID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return false;
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maSinhVien.trim());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Xóa thành công Khoa có mã: " + maSinhVien + " khỏi Database.");
                return true;
            } else {
                System.out.println("Không tìm thấy Khoa có mã: " + maSinhVien + " trong Database để xóa.");
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

    public ArrayList<SinhVien> selectStudent() {
        String sql = "SELECT StudentID, StudentName, DateOfBirth, HomeroomClass  FROM SinhVien";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<SinhVien> danhSachSinhVien = new ArrayList<>();
        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return danhSachSinhVien;
            }

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String maSinhVien = rs.getString("StudentID");
                String tenSinhVien = rs.getString("StudentName");
                LocalDate ngaySinh = rs.getDate("DateOfBirth").toLocalDate();
                String lopChuQuan = rs.getString("HomeroomClass");
                SinhVien sv = new SinhVien(maSinhVien, tenSinhVien, ngaySinh,lopChuQuan);
                danhSachSinhVien.add(sv);
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
        return danhSachSinhVien;
    }

//public ArrayList<Lop> selectClassesByStudentID(String mssv, qlyMonHocDAO qlmh, qlyKyHocDAO qlkh, qlyGiangVienDAO qlgv) {
//    ArrayList<Lop> danhSachLop = new ArrayList<>();
//    String sql = "SELECT l.ClassID, l.ClassName, l.SubjectID, l.SemesterID, l.TeacherID " +
//                 "FROM Lop l " + // Giả sử bảng lớp học tên là 'Lop' (như code bạn dùng)
//                 "JOIN DangKy dk ON l.ClassID = dk.ClassID " +
//                 "WHERE dk.StudentID = ?";
//    
//    Connection conn = null;
//    PreparedStatement pstmt = null;
//    ResultSet rs = null;
//
//    try {
//        conn = dbConnect.openConnection();
//        if (conn == null) { return danhSachLop; }
//
//        pstmt = conn.prepareStatement(sql);
//        pstmt.setString(1, mssv.trim());
//        rs = pstmt.executeQuery();
//
//        while (rs.next()) {
//            String maLopHoc = rs.getString("ClassID");
//            String tenLopHoc = rs.getString("ClassName");
//            String maMonHoc = rs.getString("SubjectID");
//            String maKyHoc = rs.getString("SemesterID");
//            String maGiangVien = rs.getString("TeacherID");
//            
//            MonHoc mh = qlmh.tim(maMonHoc);
//            KyHoc kh = qlkh.tim(maKyHoc);
//            GiangVien gv = qlgv.tim(maGiangVien);
//            
//            Lop lop = new Lop(maLopHoc, tenLopHoc, mh, gv, kh);
//            danhSachLop.add(lop);
//        }
//
//    } catch (SQLException e) {
//        System.err.println("Lỗi SQL khi truy vấn danh sách lớp của sinh viên: " + e.getMessage());
//    } finally {
//        try {
//            if (rs != null) rs.close();
//            if (pstmt != null) pstmt.close();
//            if (conn != null) conn.close();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
//    return danhSachLop;
//}
    public ArrayList<SinhVien> selectStudentByClassID(String maLop) {
    ArrayList<SinhVien> danhSachSV = new ArrayList<>();
    // Truy vấn lấy danh sách sinh viên cùng lớp với sinh viên có mã studentIDInput
    String sql = "SELECT StudentID, StudentName, DateOfBirth, HomeroomClass " +
                 "FROM SinhVien " +
                 "WHERE HomeroomClass = ? ORDER BY STT_Nhap ASC";


    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = dbConnect.openConnection();
        if (conn == null) return danhSachSV;

        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, maLop.trim());
        rs = pstmt.executeQuery();

        while (rs.next()) {
            // Khởi tạo đối tượng SinhVien từ kết quả truy vấn
            SinhVien sv = new SinhVien();
            sv.setMssv(rs.getString("StudentID"));
            sv.setTen(rs.getString("StudentName"));
            sv.setNgaySinh(rs.getDate("DateOfBirth").toLocalDate());
            sv.setLopChuQuan(rs.getString("HomeroomClass"));

            danhSachSV.add(sv);
        }

    } catch (SQLException e) {
        System.err.println("Lỗi SQL: " + e.getMessage());
    } finally {
        // Đóng tài liệu để tránh rò rỉ bộ nhớ
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    return danhSachSV; 
}
}
