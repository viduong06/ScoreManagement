package Database;

import Model.Khoa;
import Model.Nganh;
import Model.QuanLyKhoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NganhDAO {

    DatabaseConnect dbConnect = new DatabaseConnect();

    public boolean insertNewMajor(Nganh nganh, String maKhoa) {
        String sql = "INSERT INTO Nganh (MajorID, MajorName, DepartmentID) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return false;
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nganh.getMaNganh().trim());
            pstmt.setString(2, nganh.getTenNganh());
            pstmt.setString(3, maKhoa);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Thêm dữ liệu thành công: " + nganh.getMaNganh());
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

    public ArrayList<Nganh> selectMajor(qlyKhoaDAO qlyKhoa) {

        String sql = "SELECT MajorID, MajorName, DepartmentID FROM Nganh";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Nganh> danhSachNganh = new ArrayList<>();
        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return danhSachNganh;
            }

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String maNganh = rs.getString("MajorID");
                String tenNganh = rs.getString("MajorName");
                String maKhoa = rs.getString("DepartmentID");

                Khoa khoa = qlyKhoa.tim(maKhoa);
                if (khoa != null) {
                    Nganh nganh = new Nganh(maNganh, tenNganh, khoa.getTenKhoa());
                    danhSachNganh.add(nganh);
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
        return danhSachNganh;
    }

    public boolean deleteMajor(String maNganh) {
        String sql = "DELETE FROM Nganh WHERE MajorID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return false;
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maNganh.trim());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Xóa thành công Khoa có mã: " + maNganh + " khỏi Database.");
                return true;
            } else {
                System.out.println("Không tìm thấy Khoa có mã: " + maNganh + " trong Database để xóa.");
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
//    public boolean updateMajor(Nganh nganh) {
//        String sql = "UPDATE Nganh SET MajorName = ?, DepartmentID = ? WHERE MajorID = ?";
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//
//        try {
//            conn = dbConnect.openConnection();
//            if (conn == null) return false;
//
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, nganh.getTenNganh());
//            pstmt.setString(2, nganh.getKhoa().getMaKhoa());
//            pstmt.setString(3, nganh.getMaNganh());
//
//            int rows = pstmt.executeUpdate();
//            return rows > 0;
//
//        } catch (SQLException e) {
//            System.err.println("Lỗi SQL khi cập nhật ngành: " + e.getMessage());
//            return false;
//        } finally {
//            try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) {}
//            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
//        }
//    }

}
