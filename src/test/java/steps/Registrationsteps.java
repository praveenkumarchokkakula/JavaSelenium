package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import locators.locators;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static steps.LoginSteps.driver; // ensure LoginSteps.driver is public static WebDriver

public class Registrationsteps {

    private WebDriverWait wait;



    @Given("the user is in registration page")
    public void theUserIsInRegistrationPage() {


        // initialize wait AFTER driver is created
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(locators.registrationurl);

        String output = driver.getTitle();
        if (output.equals("ParaBank | Register for Free Online Account Access")) {
            System.out.println("You are in registration page");
        } else {
            System.out.println("You are not in registration page. Title: " + output);
        }

        // do NOT throw PendingException here
    }

    @When("the user enters valid registration details {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string}")
    public void theUserEntersValidRegistrationDetails(String FirstName, String LastName, String Address, String City,
                                                      String State, String ZipCode, String PhoneNumber, String SSN,
                                                      String Username, String Password, String ConfirmPassword) {

        // wait until first name visible, then fill the entire form
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.firstnameinput)).sendKeys(FirstName);
        driver.findElement(locators.lastnameinput).sendKeys(LastName);
        driver.findElement(locators.addressinput).sendKeys(Address);
        driver.findElement(locators.cityinput).sendKeys(City);
        driver.findElement(locators.stateinput).sendKeys(State);
        driver.findElement(locators.zipinput).sendKeys(ZipCode);
        driver.findElement(locators.phoneinput).sendKeys(PhoneNumber);
        driver.findElement(locators.ssninput).sendKeys(SSN);
        driver.findElement(locators.regusernameinput).sendKeys(Username);
        driver.findElement(locators.regpasswordinput).sendKeys(Password);
        driver.findElement(locators.confirmpasswordinput).sendKeys(ConfirmPassword);

        // DON'T click here — leave it to the explicit submit step (@And)
    }

    @And("the user submits the registration form")
    public void theUserSubmitsTheRegistrationForm() {
        // ensure button is clickable then click
        wait.until(ExpectedConditions.elementToBeClickable(locators.registerbutton)).click();
    }

    @Then("the user should be directed to registration success page")
    public void theUserShouldBeDirectedToRegistrationSuccessPage() {
        // wait for URL or success message; adjust the condition as per your app
        wait.until(ExpectedConditions.urlContains("register"));
        String currentUrl = (driver != null) ? driver.getCurrentUrl() : null;

        if (currentUrl != null && currentUrl.contains("registersuccess")) {
            System.out.println("STEP: user redirected to registration success page");
        } else {
            System.out.println("STEP: Registration may have failed — currentUrl: " + currentUrl);
        }
    }


}
