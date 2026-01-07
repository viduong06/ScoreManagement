/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author quochuy
 */
public class DatabaseConnect {
    private static final String SERVER_NAME = "localhost\\SQLEXPRESS"; 
    private static final int PORT_NUMBER = 1433; 
    private static final String DATABASE_NAME = "Doanoop";
    
    private static final Authentication_Mode AUTH_MODE = Authentication_Mode.SQL_SERVER_AUTH;
    private static final String SQL_USER = "sa"; 
    private static final String SQL_PASSWORD = "tam"; 

    private enum Authentication_Mode {
        SQL_SERVER_AUTH, WINDOWS_AUTH
    }
    
    public Connection openConnection() {
        String url;
        Connection conn = null;

        if (AUTH_MODE == Authentication_Mode.WINDOWS_AUTH) {
            url = "jdbc:sqlserver://" + SERVER_NAME + ":" + PORT_NUMBER + ";" 
                + "databaseName=" + DATABASE_NAME + ";"
                + "integratedSecurity=true;"
                + "trustServerCertificate=true";
            
            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.err.println("Lỗi kết nối (Windows Auth): " + e.getMessage());
                return null;
            }
            
        } else { 
            url = "jdbc:sqlserver://" + SERVER_NAME + ":" + PORT_NUMBER + ";" 
                + "databaseName=" + DATABASE_NAME + ";"
                + "encrypt=true;"
                + "trustServerCertificate=true";
                
            try {
                conn = DriverManager.getConnection(url, SQL_USER, SQL_PASSWORD);
            } catch (SQLException e) {
                System.err.println("Lỗi kết nối (SQL Server Auth): " + e.getMessage());
                return null;
            }   
        }
        return conn;
    }
}
