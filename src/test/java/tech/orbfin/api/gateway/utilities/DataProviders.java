package tech.orbfin.api.gateway.utilities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DataProviders {
    private final String accountPath;
    private final String adminPath;
    private final String authPath;
    private final String changePath;
    private final String emailPath;
    private final String passwordPath;

    @Autowired
    public DataProviders() {
        accountPath = System.getProperty("user.dir") + "/data/User.xlsx";
        adminPath = System.getProperty("user.dir") + "/data/User.xlsx";
        authPath = System.getProperty("user.dir") + "/data/User.xlsx";
        changePath = System.getProperty("user.dir") + "/data/User.xlsx";
        emailPath = System.getProperty("user.dir") + "/data/User.xlsx";
        passwordPath = System.getProperty("user.dir") + "/data/User.xlsx";
    }

    @DataProvider(name = "Account")
    public String[][] getAccountData() throws IOException {
        return getData(accountPath, "Account");
    }

    @DataProvider(name = "Admin")
    public String[][] getAdminData() throws IOException {
        return getData(adminPath, "Admin");
    }

    @DataProvider(name = "Auth")
    public String[][] getAuthData() throws IOException {
        return getData(authPath, "Auth");
    }

    @DataProvider(name = "Change")
    public String[][] getUserData() throws IOException {
        return getData(changePath, "Change");
    }

    @DataProvider(name = "Email")
    public String[][] getEmailData() throws IOException {
        return getData(emailPath, "Email");
    }

    @DataProvider(name = "Password")
    public String[][] getPasswordData() throws IOException {
        return getData(passwordPath, "Password");
    }

    private String[][] getData(String filePath, String sheetName) throws IOException {
        Excel excelFile = new Excel(filePath);
        int rowNum = excelFile.getRowCount(sheetName);
        int colCount = excelFile.getCellCount(sheetName, 1);
        List<String[]> apiDataList = new ArrayList<>();

        for (int i = 1; i <= rowNum; i++) {
            String[] rowData = new String[colCount];
            boolean validRow = true;

            for (int j = 0; j < colCount; j++) {
                String cell = excelFile.getCellData(sheetName, i, j);

                if (log.isDebugEnabled()) {
                    log.debug("Cell value: {}", cell);
                }

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
}
