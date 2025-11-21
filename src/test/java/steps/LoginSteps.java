package steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.After;
import locators.locators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginSteps {
    public static WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void beforeScenario() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        // Allow running headless via -Dheadless=true (optional)
        String headless = System.getProperty("headless", "false");
        if (headless.equalsIgnoreCase("true")) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        } else {
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // initialize wait AFTER driver is created
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Given("the user is in login page")
    public void the_user_is_in_login_page() {
        driver.get(locators.loginurl);
        System.out.println("STEP: user is on login page (navigated to " + locators.loginurl + ")");
    }

    @When("the user enters valid login details {string} and {string}")
    public void the_user_enters_valid_login_details_and(String username, String password) {
        System.out.println("STEP: user enters valid login details");

        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.usernameinput)).sendKeys(username);
        driver.findElement(locators.passwordinput).sendKeys(password);
        driver.findElement(locators.loginbutton).click();
    }

    @Then("the user should be directed to user home page")
    public void the_user_should_be_directed_user_home_page() {
        // wait for URL to contain overview (reduces flakiness)
        try {
            wait.until(ExpectedConditions.urlContains("overview"));
            String currentUrl = driver.getCurrentUrl();
            System.out.println("STEP: user redirected to home page, url: " + currentUrl);
            Assert.assertTrue(currentUrl.contains("overview"), "User successfully logged in.");
        } catch (Exception e) {
            String currentUrl = (driver != null) ? driver.getCurrentUrl() : null;
            System.out.println("STEP: login failed — current URL: " + currentUrl);
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
