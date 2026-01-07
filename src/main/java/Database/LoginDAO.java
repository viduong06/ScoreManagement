/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author quochuy
 */
public class LoginDAO {

    DatabaseConnect dbConnect = new DatabaseConnect();

    public boolean insertNewAccount(String acc, String pass, String role) {
        String sql = "INSERT INTO Login (Account, Password, Role) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return false;
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, acc);
            pstmt.setString(2, pass);
            pstmt.setString(3, role);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Thêm dữ liệu thành công: " + acc);
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

    public boolean checkTK(String acc) {
        String sql = "SELECT COUNT(*) FROM Login WHERE Account = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return false;
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, acc);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi kiểm tra tồn tại tài khoản: " + e.getMessage());
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
            }
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
            }
        }
    }

    public boolean checkLogin(String acc, String pass, String role) {
        String sql = "SELECT COUNT(*) FROM Login WHERE Account COLLATE Latin1_General_CS_AS = ? AND Password COLLATE Latin1_General_CS_AS = ? AND Role = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return false;
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, acc);
            pstmt.setString(2, pass);
            pstmt.setString(3, role);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) == 1;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi đăng nhập: " + e.getMessage());
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
            }
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
            }
        }
    }

    public boolean deleteAccount(String taiKhoan) {
        String sql = "DELETE FROM Login WHERE Account = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbConnect.openConnection();
            if (conn == null) {
                return false;
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, taiKhoan.trim());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Xóa thành công ta có mã: " + taiKhoan + " khỏi Database.");
                return true;
            } else {
                System.out.println("Không tìm thấy Khoa có mã: " + taiKhoan + " trong Database để xóa.");
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
}
