package tech.orbfin.api.gateway.utilities;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;

import java.io.IOException;

@Slf4j
public class DataProviders {

    @DataProvider(name = "RequestSignup")
    public String[][] getRequestRegisterData() throws IOException {
        String path = System.getProperty("user.dir") + "/data/User.xlsx";
        Excel excelFile = new Excel(path);

        int rowNum = excelFile.getRowCount("RequestSignup");
        int colCount = excelFile.getCellCount("RequestSignup", 1);
        String[][] apiData = new String[rowNum][colCount];

        for (int i = 1; i <= rowNum; i++) {
            for (int j = 0; j < colCount; j++) {
                apiData[i - 1][j] = excelFile.getCellData("RequestSignup", i, j);
            }
        }

        return apiData;
    }
}
