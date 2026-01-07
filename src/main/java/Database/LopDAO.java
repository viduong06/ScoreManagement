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
import java.util.ArrayList;

public class LopDAO {

    DatabaseConnect dbConnect = new DatabaseConnect();
// Các chức năng của Lớp học

    public boolean insertNewClass(Lop lop, String maGV) {
        String sql = "INSERT INTO Lop (ClassID, ClassName, TeacherID) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return false;
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, lop.getMaLop().trim());
            pstmt.setString(2, lop.getTenLop().trim());
            pstmt.setString(3, maGV);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Thêm dữ liệu thành công: " + lop.getTenLop());
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
    public boolean deleteClass(String maLopHoc) {
        String sql = "DELETE FROM Lop WHERE ClassID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return false;
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maLopHoc.trim());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Xóa thành công Khoa có mã: " + maLopHoc + " khỏi Database.");
                return true;
            } else {
                System.out.println("Không tìm thấy Khoa có mã: " + maLopHoc + " trong Database để xóa.");
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

    public ArrayList<Lop> selectClass(qlyGiangVienDAO qlgv) {
        String sql = "SELECT ClassID, ClassName, TeacherID FROM Lop";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Lop> danhSachLopHoc = new ArrayList<>();
        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return danhSachLopHoc;
            }

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String maLopHoc = rs.getString("ClassID");
                String tenLopHoc = rs.getString("ClassName");
                String MaGiangVien = rs.getString("TeacherID");
                GiangVien gv = qlgv.tim(MaGiangVien);
                if (gv != null) {
                    Lop lop = new Lop(maLopHoc, tenLopHoc, gv.getTenGiangVien(), gv.getSDT());
                    danhSachLopHoc.add(lop);
                } else {
                    System.out.println("Looix");
                }

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
        return danhSachLopHoc;
    }

//    public ArrayList<SinhVien> selectStudentsByClassID(String maLop, qlyKhoaDAO qlk, qlyNganhDAO qln) {
//        ArrayList<SinhVien> dsSinhVien = new ArrayList<>();
//        String sql = "SELECT s.StudentID, s.StudentName, s.DepartmentID, s.MajorID "
//                + "FROM SinhVien s "
//                + "JOIN DangKy dk ON s.StudentID = dk.StudentID "
//                + "WHERE dk.ClassID = ?";
//
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//        try {
//            conn = dbConnect.openConnection();
//            if (conn == null) {
//                return dsSinhVien;
//            }
//
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, maLop.trim());
//            rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                String maSV = rs.getString("StudentID");
//                String tenSV = rs.getString("StudentName");
//                String maKhoa = rs.getString("DepartmentID");
//                String maNganh = rs.getString("MajorID");
//
//                Khoa khoa = qlk.tim(maKhoa);
//                Nganh nganh = qln.tim(maNganh);
//
//                SinhVien sv = new SinhVien(maSV, tenSV, khoa, nganh);
//                dsSinhVien.add(sv);
//            }
//
//        } catch (SQLException e) {
//            System.err.println("Lỗi SQL khi truy vấn danh sách sinh viên của lớp: " + e.getMessage());
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return dsSinhVien;
//    }

    public ArrayList<Lop> selectClassesByTeacherID(String maGiangVien, qlyGiangVienDAO qlgv) {
        String sql = "SELECT ClassID, ClassName, TeacherID FROM Lop WHERE TeacherID = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Lop> danhSachLopTheoGV = new ArrayList<>();

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return danhSachLopTheoGV;
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maGiangVien.trim());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String maLopHoc = rs.getString("ClassID");
                String tenLopHoc = rs.getString("ClassName");
                String maGiangVienFK = rs.getString("TeacherID");

                GiangVien gv = qlgv.tim(maGiangVienFK);

                if (gv != null) {
                    Lop lop = new Lop(maLopHoc, tenLopHoc, gv.getTenGiangVien(), gv.getSDT());
                    danhSachLopTheoGV.add(lop);
                } else {
                    System.out.println("Lỗi: Không tìm thấy thông tin giảng viên cho lớp " + tenLopHoc);
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi truy vấn danh sách Lớp theo Giảng Viên: " + e.getMessage());
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
        return danhSachLopTheoGV;
    }
}
