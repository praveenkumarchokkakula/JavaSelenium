package steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginSteps {

    public static WebDriver driver;

    @Given("the user is in login page")
    public void the_user_is_in_login_page() {
        // Suppress noisy Selenium DevTools/CDP warnings by raising their logger level
//        try {
//            Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.SEVERE);
//            Logger.getLogger("org.openqa.selenium.chromium.ChromiumDriver").setLevel(Level.SEVERE);
//            Logger.getLogger("org.openqa.selenium").setLevel(Level.WARNING);
//        } catch (Exception e) {
//            // If logging config can't be updated, continue without failing tests
//            System.out.println("Could not adjust Selenium logger levels: " + e.getMessage());
//        }

        // Initialize ChromeDriver (headless) and navigate to google.co
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // Allow turning headless on/off via system property `-Dheadless=false` (default: true)
        // Run headless by default, but allow the caller to disable it for debugging/visual runs
//        String headlessProp = System.getProperty("headless", "true");
//        boolean headless = !headlessProp.equalsIgnoreCase("false");
//        if (headless) {
//            options.addArguments("--headless=new");
//            options.addArguments("--no-sandbox");
//            options.addArguments("--disable-dev-shm-usage");
//        } else {
//            // Visible browser for debugging; set a reasonable window size
//            options.addArguments("--window-size=1920,1080");
//        }
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://parabank.parasoft.com/parabank/index.htm");

        // If running in visible mode, optionally pause so you can see/interact with the browser.
        // Use -DpauseOnOpen=true to enable (default: false).
//        String pauseProp = System.getProperty("pauseOnOpen", "false");
//        boolean pauseOnOpen = pauseProp.equalsIgnoreCase("true");
//        if (!headless && pauseOnOpen) {
//            System.out.println("Browser opened in visible mode. Press ENTER to continue and close it...");
//            try { System.in.read(); } catch (Exception ignored) { }
//        }

        System.out.println("STEP: user is on login page (navigated to https://parabank.parasoft.com/parabank/index.htm)");
    }


    @When("the user enters valid login details {string} and {string}")
    public void the_user_enters_valid_login_details_and(String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("STEP: user enters valid login details");
    }

    @Then("the user should be directed to user home page")
    public void the_user_should_be_directed_user_home_page() {

        // Safely get current URL
        String currentUrl = (driver != null) ? driver.getCurrentUrl() : null;

        if (currentUrl != null && currentUrl.contains("overview")) {
            System.out.println("STEP: user redirected to home page");
            Assert.assertTrue(true, "User successfully logged in.");
        } else {
            System.out.println("STEP: Invalid user credentials — login failed");
            Assert.fail("Invalid user credentials — Expected URL to contain 'overview' but was: " + currentUrl);
        }
    }


    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
