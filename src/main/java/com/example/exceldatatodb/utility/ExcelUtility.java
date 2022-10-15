package com.example.exceldatatodb.utility;

import com.example.exceldatatodb.entity.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtility {
    public static boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))//for xlsx format
            return true;
        return false;
    }

    //it converts excel to list of product
    public static List<Product> convertExcelToList(InputStream is) {
        List<Product> list = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row row = iterator.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                Product prod = new Product();
                int cellId = 0;
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cellId) {
                        case 0:
                            prod.setProductId((int) cell.getNumericCellValue());
                            break;
                        case 1:
                            prod.setProductName(cell.getStringCellValue());
                            break;
                        case 2:
                            prod.setProductDesc(cell.getStringCellValue());
                            break;
                        case 3:
                            prod.setProductPrice(cell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                    cellId++;
                }
                list.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
