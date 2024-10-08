package tech.orbfin.api.gateway.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class Excel {
    public FileInputStream fis;
    public FileOutputStream fos;
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;
    public CellStyle style;
    String path;

    public Excel(String path) {
        this.path = path;
    }

    public int getRowCount(String sheetName) throws IOException {
        fis = new FileInputStream(path);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum();

        workbook.close();
        fis.close();

        return rowCount;
    }

    public int getCellCount(String sheetName, int rowNum) throws IOException {
        fis = new FileInputStream(path);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowNum);
        int cellCount = row.getLastCellNum();

        workbook.close();
        fis.close();

        return cellCount;
    }

    public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
        try (FileInputStream fis = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet != null) {
                Row row = sheet.getRow(rowNum);

                if (row != null) {
                    Cell cell = row.getCell(colNum);
                    if (cell != null) {
                        DataFormatter formatter = new DataFormatter();
                        String cellValue = formatter.formatCellValue(cell);

                        if (!cellValue.isEmpty()) {
                            return cellValue.isEmpty() ? null : cellValue;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setCellData(String sheetName, int rowNum, int colNum, String data) throws IOException {
        File excelFile = new File(path);

        if (!excelFile.exists()) {
            workbook = new XSSFWorkbook();
            fos = new FileOutputStream(path);
            workbook.write(fos);
        }

        fis = new FileInputStream(path);
        workbook = new XSSFWorkbook(fis);

        if (workbook.getSheetIndex(sheetName) == -1) {
            workbook.createSheet(sheetName);
            sheet = workbook.getSheet(sheetName);
        }

        if (sheet.getRow(rowNum) == null) {
            sheet.createRow(rowNum);
            row = sheet.getRow(rowNum);
        }

        cell = row.createCell(colNum);
        cell.setCellValue(data);
        fos = new FileOutputStream(path);

        workbook.write(fos);
        workbook.close();

        fis.close();
        fos.close();
    }
}