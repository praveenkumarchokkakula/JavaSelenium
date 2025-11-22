package Utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {

    /**
     * Reads an XLSX sheet and returns a list of maps where each map is a row (header->cellValue).
     *
     * @param filePath  path to the .xlsx file (e.g. src/test/resources/Testdata/LoginData.xlsx)
     * @param sheetName the sheet name (e.g. "Sheet1")
     * @return list of rows as maps
     */
    public static List<Map<String, String>> getData(String filePath, String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalArgumentException("Header row (row 0) is missing in sheet: " + sheetName);
            }

            int lastRow = sheet.getLastRowNum();
            int lastCol = headerRow.getLastCellNum(); // number of header columns

            for (int i = 1; i <= lastRow; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    // skip completely blank row
                    continue;
                }

                Map<String, String> rowData = new HashMap<>();

                for (int j = 0; j < lastCol; j++) {
                    String key = formatter.formatCellValue(headerRow.getCell(j)).trim();
                    if (key.isEmpty()) {
                        // skip unnamed header columns
                        continue;
                    }

                    String value = "";
                    if (row.getCell(j) != null) {
                        value = formatter.formatCellValue(row.getCell(j)).trim();
                    }
                    rowData.put(key, value);
                }

                // Only add non-empty rows (optional)
                if (!rowData.isEmpty()) {
                    data.add(rowData);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            // optionally rethrow as runtime to fail fast in CI:
            // throw new RuntimeException(e);
        }

        return data;
    }
}
