package com.gianfranco.trabajoparcial.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelReader {
    public static String[][] read(String path) {
        String[][] list = null;
        int i = 0;
        try {
            FileInputStream file = new FileInputStream(new File(path));
            HSSFWorkbook excelFile = new HSSFWorkbook(file);
            HSSFSheet sheet = excelFile.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            list = new String[sheet.getLastRowNum()][];
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Iterator<Cell> cells = currentRow.cellIterator();
                list[i] = new String[currentRow.getLastCellNum()];
                int j = 0;
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    list[i][j] = cell.getStringCellValue();
                    j++;
                }
                i++;
            }
            excelFile.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
