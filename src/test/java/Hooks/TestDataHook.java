package Hooks;

import Utils.ExcelReader;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TestDataHook {

    // loaded once when class is initialized
    public static final List<Map<String, String>> excelData =
            ExcelReader.getData("src/test/resources/Testdata/LoginData.xlsx", "Sheet1");

    // counter to hand out rows, incremented per scenario
    private static final AtomicInteger rowCounter = new AtomicInteger(0);

    // per-thread/per-scenario storage so steps can access the data
    private static final ThreadLocal<Map<String, String>> currentRow = new ThreadLocal<>();

    /**
     * Call this in @Before hook so each scenario gets next Excel row
     */
    @Before(order = 0)
    public void setupTestData(Scenario scenario) {
        int idx = rowCounter.getAndIncrement();

        if (excelData == null || excelData.isEmpty()) {
            throw new RuntimeException("No Excel data found in LoginData.xlsx - ensure file path and sheet name are correct.");
        }

        if (idx >= excelData.size()) {
            // optional: reset to 0 to reuse rows, or throw to fail fast
            throw new RuntimeException("Not enough rows in Excel for scenario: " + scenario.getName() +
                    ". Needed index: " + idx + ", available: " + excelData.size());
        }

        Map<String, String> row = excelData.get(idx);
        currentRow.set(row);

        // Optionally attach which Excel row to the report
        scenario.log("Using Excel row index: " + idx + " for scenario '" + scenario.getName() + "'");
    }

    // helper for steps to read values
    public static String get(String key) {
        Map<String, String> map = currentRow.get();
        return (map == null) ? null : map.getOrDefault(key, "");
    }

    // optional: clear after scenario
    @Before(order = 1000)
    public void noopClear() {
        // you could add an @After hook to clear ThreadLocal if you prefer
    }
}
