package tech.orbfin.api.gateway.utilities;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DataProviders {

    @DataProvider(name = "RequestSignup")
    public String[][] getRequestRegisterData() throws IOException {
        String path = System.getProperty("user.dir") + "/data/User.xlsx";
        Excel excelFile = new Excel(path);

        int rowNum = excelFile.getRowCount("RequestSignup");
        int colCount = excelFile.getCellCount("RequestSignup", 1);
        List<String[]> apiDataList = new ArrayList<>();

        for (int i = 1; i <= rowNum; i++) {
            String[] rowData = new String[colCount];
            boolean validRow = true;

            for (int j = 0; j < colCount; j++) {
                String cell = excelFile.getCellData("RequestSignup", i, j);

                if (cell == null || cell.isEmpty()) {
                    validRow = false;
                    break;
                }

                rowData[j] = cell;
            }

            if (validRow) {
                apiDataList.add(rowData);
            }
        }

        return apiDataList.toArray(new String[0][0]);
    }

    @DataProvider(name = "RequestLogin")
    public String[][] getRequestLoginData() throws IOException {
        String path = System.getProperty("user.dir") + "/data/User.xlsx";
        Excel excelFile = new Excel(path);

        int rowNum = excelFile.getRowCount("RequestLogin");
        int colCount = excelFile.getCellCount("RequestLogin", 1);
        List<String[]> apiDataList = new ArrayList<>();

        for (int i = 1; i <= rowNum; i++) {
            String[] rowData = new String[colCount];
            boolean validRow = true; // Track if the entire row is valid

            for (int j = 0; j < colCount; j++) {
                String cell = excelFile.getCellData("RequestLogin", i, j);

                if (cell == null || cell.isEmpty()) {
                    validRow = false; // Mark the row as invalid if any cell is empty
                    break; // Exit inner loop
                }

                rowData[j] = cell; // Store cell data in the rowData array
            }

            // Add rowData to the list only if the row is valid
            if (validRow) {
                apiDataList.add(rowData);
            }
        }

        // Convert List to a 2D array and return
        return apiDataList.toArray(new String[0][0]);
    }
}
