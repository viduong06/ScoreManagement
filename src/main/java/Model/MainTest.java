//package Model; // Ví dụ: đặt trong package Test
//
//import Model.*;// Cần import lớp Khoa
//import java.util.Scanner;
//
//public class MainTest {
//
//    public static void main(String[] args) {
//        /*
//        // 1. KHỞI TẠO VÀ TẢI DỮ LIỆU
//        System.out.println("=== 1. KHỞI TẠO VÀ TẢI DỮ LIỆU BAN ĐẦU ===");
//        QuanLyKhoa qlKhoa = new QuanLyKhoa();
//        QuanLyNganh qlNganh = new QuanLyNganh(qlKhoa);
//        // loadDataFromDatabase() đã được gọi tự động trong Constructor
//        
//        System.out.println("\n-------------------------------------------");
//        
//        // 2. TẠO CÁC ĐỐI TƯỢNG KHOA MỚI
//        Khoa k1 = new Khoa("CNTT", "Công Nghệ Thông Tin");
//        Khoa k2 = new Khoa("KT", "Kinh Tế");
//        Khoa k3 = new Khoa("XD", "Xây Dựng");
//        
//        Nganh k11 = new Nganh("CNPM", "Công nghệ phần mềm", k1);
//        Nganh k12 = new Nganh("CNPM2", "Công nghệ phần mềm2", k2);
//
//        // 3. THÊM DỮ LIỆU VÀO DATABASE VÀ LIST
//        System.out.println("=== 2. THÊM DỮ LIỆU (INSERT) ===");
//        if (qlKhoa.them(k1)) {
//            System.out.println("Thêm thành công: " + k1.getTenKhoa());
//        } else {
//            System.out.println("Thêm thất bại (kiểm tra DatabaseConnector)");
//        }
//        
//        qlKhoa.them(k2);
//        qlKhoa.them(k3);
//        qlNganh.them(k12);
//        qlNganh.them(k11);
//        
//        qlKhoa.xoa("CNTT");
//        
//        System.out.println("\n-------------------------------------------");
//
//        
//        System.out.println("\n-------------------------------------------");*/
//        
//        DangNhap quanLyDangNhap = new DangNhap();
//        Scanner sc = new Scanner(System.in);
//        
//        String testUser = "testuser1";
//        String testPass = "123456";
//        
//        System.out.println("--- Bắt đầu chạy thử ---");
//        
//        // --- CHẠY THỬ ĐĂNG KÝ (REGISTER) ---
//        System.out.println("\n[1] Thử Đăng ký tài khoản: " + testUser);
//        
//        // 1. Đăng ký lần đầu tiên
//        boolean success1 = quanLyDangNhap.register(testUser, testPass);
//        if (success1) {
//            System.out.println("  -> Đăng ký LẦN 1 thành công! (Dữ liệu đã vào DB)");
//        } else {
//            System.out.println("  -> Đăng ký LẦN 1 THẤT BẠI. (Kiểm tra CSDL và kết nối)");
//        }
//        
//        // 2. Thử đăng ký lại (để kiểm tra hàm checkTK)
//        System.out.println("\n[2] Thử Đăng ký lại tài khoản: " + testUser);
//        boolean success2 = quanLyDangNhap.register(testUser, testPass);
//        if (success2) {
//            System.out.println("  -> Đăng ký LẦN 2 (trùng) THÀNH CÔNG! (LỖI LOGIC!)");
//        } else {
//            System.out.println("  -> Đăng ký LẦN 2 (trùng) THẤT BẠI. (ĐÚNG, tài khoản đã tồn tại)");
//        }
//
//        // --- CHẠY THỬ ĐĂNG NHẬP (LOGIN) ---
//        System.out.println("\n[3] Thử Đăng nhập với thông tin đúng.");
//        boolean loginCorrect = quanLyDangNhap.login(testUser, testPass);
//        if (loginCorrect) {
//            System.out.println("  -> ĐĂNG NHẬP THÀNH CÔNG!");
//        } else {
//            System.out.println("  -> Đăng nhập THẤT BẠI. (Lỗi logic, sai mật khẩu, hoặc lỗi DB)");
//        }
//
//        System.out.println("\n[4] Thử Đăng nhập với mật khẩu sai.");
//        boolean loginWrong = quanLyDangNhap.login(testUser, "sai_mat_khau");
//        if (loginWrong) {
//            System.out.println("  -> Đăng nhập sai mật khẩu THÀNH CÔNG! (LỖI LOGIC!)");
//        } else {
//            System.out.println("  -> Đăng nhập sai mật khẩu THẤT BẠI. (ĐÚNG)");
//        }
//        
//        sc.close();
//    }
//}