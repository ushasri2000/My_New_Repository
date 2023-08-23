//package com.digi.springbootfirstapp;//package com.digi.springbootfirstapp;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.Iterator;
//
//import static org.apache.poi.ss.usermodel.Cell.*;
//
//public class XLSXReaderExample {
//
//    public static String parseXLSX(InputStream inputStream) {
//        StringBuilder parsedAttributes = new StringBuilder();
//
//        try {
//            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
//            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
//                XSSFSheet sheet = wb.getSheetAt(i);
//                Iterator<Row> itr = sheet.iterator();
//                while (itr.hasNext()) {
//                    Row row = itr.next();
//                    Iterator<Cell> cellIterator = row.cellIterator();
//                    while (cellIterator.hasNext()) {
//                        Cell cell = cellIterator.next();
//                        switch (cell.getCellType()) {
//                            case Cell.CELL_TYPE_STRING:
//                                parsedAttributes.append(cell.getStringCellValue()).append("\t\t\t<br>");
//                                break;
//                            case Cell.CELL_TYPE_NUMERIC:
//                                parsedAttributes.append(cell.getNumericCellValue()).append("\t\t\t<br>");
//                                break;
//                            default:
//                        }
//                    }
//                    parsedAttributes.append("<br>");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return parsedAttributes.toString();
//    }
//}



package com.digi.springbootfirstapp;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XLSXReaderExample {

    public static String parseXLSX(InputStream inputStream, String desiredValue) {
        StringBuilder parsedAttributes = new StringBuilder();

        try {
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                XSSFSheet sheet = wb.getSheetAt(i);

                Iterator<Row> itr = sheet.iterator();
                while (itr.hasNext()) {
                    Row row = itr.next();

                    // Get the cell from the first column
                    Cell targetCell = row.getCell(0);
                    if (targetCell != null && isCellMatching(targetCell, desiredValue)) {
                        // Append cell values to the output
                        Iterator<Cell> cellIterator = row.cellIterator();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cell.getCellType()) {
                                case Cell.CELL_TYPE_STRING:
                                    parsedAttributes.append(cell.getStringCellValue()).append(",");
                                    break;
                                case Cell.CELL_TYPE_NUMERIC:
                                    parsedAttributes.append(cell.getNumericCellValue()).append(",");
                                    break;
                                default:
                                    parsedAttributes.append(",\t");
                            }
                        }
                        parsedAttributes.deleteCharAt(parsedAttributes.length() - 1); // Remove last comma
                        parsedAttributes.append("<br>");
                    }
                }
                parsedAttributes.append("<br>"); // Line break between sheets
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsedAttributes.toString();
    }

    private static boolean isCellMatching(Cell cell, String desiredValue) {
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return desiredValue.equals(cell.getStringCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            double numericCellValue = cell.getNumericCellValue();
            String numericValueString = String.valueOf((int) numericCellValue);
            return numericValueString.equals(desiredValue);
        } else {
            return false;
        }
    }

}




