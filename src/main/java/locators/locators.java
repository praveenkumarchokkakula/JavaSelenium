package locators;

import org.openqa.selenium.By;

public class locators {
//loginlocators
    public static String loginurl="https://parabank.parasoft.com/parabank/index.htm";
    public static By usernameinput=By.name("username");
    public static By passwordinput=By.name("password");
    public static By loginbutton=By.xpath("//input[@value='Log In']");

//registraionlocators
    public static  String registrationurl="https://parabank.parasoft.com/parabank/register.htm";
    public static By registerlink=By.linkText("Register");
    public static By firstnameinput = By.xpath("//input[@id='customer.firstName']");
    public static By lastnameinput = By.xpath("//input[@id='customer.lastName']");
    public static By addressinput = By.xpath("//input[@id='customer.address.street']");
    public static By cityinput = By.xpath("//input[@id='customer.address.city']");
    public static By stateinput = By.xpath("//input[@id='customer.address.state']");
    public static By zipinput = By.xpath("//input[@id='customer.address.zipCode']");
    public static By phoneinput = By.xpath("//input[@id='customer.phoneNumber']");
    public static By ssninput = By.xpath("//input[@id='customer.ssn']");
    public static By regusernameinput = By.xpath("//input[@id='customer.username']");
    public static By regpasswordinput = By.xpath("//input[@id='customer.password']");
    public static By confirmpasswordinput = By.xpath("//input[@id='repeatedPassword']");
    public static By registerbutton = By.xpath("//input[@value='Register']");
}