package Model;

import Database.DatabaseConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

public class DangNhap {
 private static String username;
    private static String role;

    public static void setUsername(String username) {
        DangNhap.username = username;
    }

    public static String getUsername() {
        return username;
    }

    public static void setRole(String role) {
        DangNhap.role = role;
    }

    public static String getRole() {
        return role;
    }

    public static void clear() {
        username = null;
        role = null;
    }
}