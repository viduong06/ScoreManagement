package Database;

import Model.DiemThi;
import Model.Lop;
import Model.SinhVien;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

    public ExcelUtil() {
    }

    public static void taoFileExcelLop(String maLop) {
        try {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Bảng điểm");

            // ===== STYLE =====
            CellStyle centerBold = wb.createCellStyle();
            Font boldFont = wb.createFont();
            boldFont.setBold(true);
            boldFont.setFontHeightInPoints((short) 14);
            centerBold.setFont(boldFont);
            centerBold.setAlignment(HorizontalAlignment.CENTER);
            centerBold.setVerticalAlignment(VerticalAlignment.CENTER);

            CellStyle headerStyle = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // ===== TIÊU ĐỀ =====
            Row r0 = sheet.createRow(0);
            r0.createCell(0).setCellValue("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM");
            r0.getCell(0).setCellStyle(centerBold);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

            Row r1 = sheet.createRow(1);
            r1.createCell(0).setCellValue("BẢNG GHI ĐIỂM LỚP HỌC PHẦN");
            r1.getCell(0).setCellStyle(centerBold);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 8));

            Row r2 = sheet.createRow(2);
            r2.createCell(0).setCellValue("Học kỳ: HK1 2025-2026  |  Lớp: " + maLop);
            r2.getCell(0).setCellStyle(centerBold);
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 8));

            // ===== HEADER (ROW 4,5) =====
            Row r4 = sheet.createRow(4);
            Row r5 = sheet.createRow(5);

            for (int c = 0; c <= 8; c++) {
                r4.createCell(c);
                r5.createCell(c);
            }

            r4.getCell(0).setCellValue("STT");
            r4.getCell(1).setCellValue("Thông tin sinh viên");
            r4.getCell(5).setCellValue("Thường xuyên");
            r4.getCell(7).setCellValue("DKT");
            r4.getCell(8).setCellValue("Điểm tổng kết");

            sheet.addMergedRegion(new CellRangeAddress(4, 5, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 4));
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 5, 6));
            sheet.addMergedRegion(new CellRangeAddress(4, 5, 7, 7));
            sheet.addMergedRegion(new CellRangeAddress(4, 5, 8, 8));

            String[] headers = {
                "Mã SV", "Tên", "Ngày sinh", "Lớp",
                "1 (25%)", "2 (25%)", "50%"
            };

            for (int i = 0; i < headers.length; i++) {
                r5.getCell(i + 1).setCellValue(headers[i]);
            }

            for (int row = 4; row <= 5; row++) {
                Row r = sheet.getRow(row);
                for (int col = 0; col <= 8; col++) {
                    r.getCell(col).setCellStyle(headerStyle);
                }
            }

            for (int i = 0; i <= 8; i++) {
                sheet.setColumnWidth(i, 5500);
            }

            int startRow = 6;          // dòng bắt đầu nhập sinh viên
            int soDongDuPhong = 100;   // cho nhập 100 SV

            for (int i = 0; i < soDongDuPhong; i++) {
                int rowIndex = startRow + i;
                Row row = sheet.createRow(rowIndex);

                for (int c = 0; c <= 8; c++) {
                    row.createCell(c);
                }

                int excelRow = rowIndex + 1;

                // Cột I: Điểm tổng kết = F*0.25 + G*0.25 + H*0.5
                row.getCell(8).setCellFormula(
                        "F" + excelRow + "*0.25 + G" + excelRow + "*0.25 + H" + excelRow + "*0.5"
                );
            }

            // ===== GHI FILE =====
            FileOutputStream fos = new FileOutputStream(maLop + ".xlsx");
            wb.write(fos);
            wb.close();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openExcelByMaLop(String maLop) {
        String path = maLop + ".xlsx";
        try {
            Desktop.getDesktop().open(new File(path));
        } catch (IOException ex) {
            System.out.println("File ko ton tai");
        }
    }

    public static void themDuLieu(String maLop) {
        try {
            FileInputStream fis = new FileInputStream(maLop + ".xlsx");
            Workbook wb = new XSSFWorkbook(fis);
            Sheet sheet = wb.getSheetAt(0);

            int startRow = 6; // Bắt đầu từ dòng 6 (sau header)

            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);

            int stt = 1;
            DangKyLopHocDAO dkDAO = new DangKyLopHocDAO();
            List<SinhVien> list = dkDAO.selectStudentsByClassID(maLop);

            for (SinhVien sv : list) {
                Row row = sheet.getRow(startRow);
                if (row == null) {
                    row = sheet.createRow(startRow);
                }

                // Lấy hoặc tạo cell nếu chưa có
                Cell cell0 = row.getCell(0) != null ? row.getCell(0) : row.createCell(0);
                Cell cell1 = row.getCell(1) != null ? row.getCell(1) : row.createCell(1);
                Cell cell2 = row.getCell(2) != null ? row.getCell(2) : row.createCell(2);
                Cell cell3 = row.getCell(3) != null ? row.getCell(3) : row.createCell(3);
                Cell cell4 = row.getCell(4) != null ? row.getCell(4) : row.createCell(4);

                // Ghi dữ liệu vào các cell
                cell0.setCellValue(stt++);
                cell1.setCellValue(sv.getMssv());
                cell2.setCellValue(sv.getTen());
                cell3.setCellValue(sv.getNgaySinh().toString());
                cell4.setCellValue(sv.getLopChuQuan());

                // set border
                cell0.setCellStyle(cellStyle);
                cell1.setCellStyle(cellStyle);
                cell2.setCellStyle(cellStyle);
                cell3.setCellStyle(cellStyle);
                cell4.setCellStyle(cellStyle);

                startRow++;
            }

            fis.close();

            FileOutputStream fos = new FileOutputStream(maLop + ".xlsx");
            wb.write(fos);
            wb.close();
            fos.close();

            System.out.println("Đã ghi dữ liệu vào Excel");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void xoaFileExcel(String maLop) {
        String fileName = maLop + ".xlsx";
        File fileToDelete = new File(fileName);
        fileToDelete.delete();
    }

public static void capNhatExcelKhiXoaSV(String maLop, String mssvXoa) {
    String fileName = maLop + ".xlsx";
    File file = new File(fileName);

    if (!file.exists()) return;

    try (FileInputStream fis = new FileInputStream(file)) {
        Workbook wb = new XSSFWorkbook(fis);
        Sheet sheet = wb.getSheetAt(0);
        int startRow = 6; 
        int lastRow = sheet.getLastRowNum();
        int rowIndexToRemove = -1;

        // BƯỚC 1: Tìm dòng chứa MSSV
        for (int i = startRow; i <= lastRow; i++) {
            Row row = sheet.getRow(i);
            if (row != null && row.getCell(1) != null) {
                if (row.getCell(1).getStringCellValue().trim().equalsIgnoreCase(mssvXoa.trim())) {
                    rowIndexToRemove = i;
                    break;
                }
            }
        }

        if (rowIndexToRemove != -1) {
            // BƯỚC 2: Xóa vật lý dòng đó
            Row rowToDelete = sheet.getRow(rowIndexToRemove);
            if (rowToDelete != null) {
                sheet.removeRow(rowToDelete);
            }

            // BƯỚC 3: Dịch chuyển hàng lên
            if (rowIndexToRemove < lastRow) {
                sheet.shiftRows(rowIndexToRemove + 1, lastRow, -1);
            }

            // BƯỚC 4: Thiết lập lại Style và Công thức cho TOÀN BỘ vùng dữ liệu còn lại
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Duyệt lại từ startRow đến dòng cuối mới (đã trừ đi 1 dòng bị xóa)
            for (int i = startRow; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // 4.1. Cập nhật STT (Cột 0)
                Cell cellSTT = row.getCell(0) == null ? row.createCell(0) : row.getCell(0);
                cellSTT.setCellValue(i - startRow + 1);
                cellSTT.setCellStyle(cellStyle);

                // 4.2. Khôi phục Công thức (Cột 8)
                int excelRow = i + 1; // Số hàng hiển thị trong Excel (ví dụ dòng index 6 là hàng 7)
                
                // Quan trọng: Xóa ô cũ nếu có và tạo lại ô mới để đảm bảo sạch công thức cũ bị lỗi
                if (row.getCell(8) != null) row.removeCell(row.getCell(8));
                Cell cellTK = row.createCell(8);
                
                // Gán lại công thức chính xác
                String formula = "F" + excelRow + "*0.25 + G" + excelRow + "*0.25 + H" + excelRow + "*0.5";
                cellTK.setCellFormula(formula);
            }

            // BƯỚC 5: Ép Excel phải tính toán lại khi người dùng mở file
            sheet.setForceFormulaRecalculation(true);
            wb.getCreationHelper().createFormulaEvaluator().evaluateAll();

            // Ghi file
            fis.close(); 
            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                wb.write(fos);
            }
            System.out.println("Cập nhật file thành công, công thức đã được khôi phục.");
        }
        wb.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public static ObservableList<DiemThi> readExcel(String maLop) {
        ObservableList<DiemThi> list = FXCollections.observableArrayList();
        String file = maLop + ".xlsx";
        try (FileInputStream fis = new FileInputStream(file); Workbook wb = new XSSFWorkbook(fis)) {

            Sheet sheet = wb.getSheetAt(0);

            // Bắt đầu từ dòng 6 (rowIndex = 6) vì dòng 0-5 là header
            for (int i = 6; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                // Kiểm tra cell MSSV có dữ liệu không
                if (row.getCell(1) == null || row.getCell(1).getStringCellValue().isBlank()) {
                    continue; // Bỏ qua dòng trống
                }

                String mssv = row.getCell(1) != null ? row.getCell(1).getStringCellValue() : "";
                String tenSV = row.getCell(2) != null ? row.getCell(2).getStringCellValue() : "";
                double diemGK1 = row.getCell(5) != null ? row.getCell(5).getNumericCellValue() : 0;
                double diemGK2 = row.getCell(6) != null ? row.getCell(6).getNumericCellValue() : 0;
                double diemCK = row.getCell(7) != null ? row.getCell(7).getNumericCellValue() : 0;
                double diemTK = row.getCell(8) != null ? row.getCell(8).getNumericCellValue() : 0;
                list.add(new DiemThi(mssv, tenSV, diemGK1, diemGK2, diemCK, diemTK));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
