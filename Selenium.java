package program;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Selenium {

    public static void main(String[] args) 
    {
        // Set the path to the chromedriver executable
        System.setProperty("webdriver.chrome.driver", "D:\\Downloads\\ChromeDriver\\chromedriver.exe");

        // Create a new instance of the Chrome driver
        WebDriver driver = new ChromeDriver();

        // Scenario 1: Log in using standard user
        login(driver, "standard_user", "secret_sauce");

        // Verify user is able to navigate to home page
        if (driver.getCurrentUrl().equals("https://www.saucedemo.com/inventory.html")) {
            System.out.println("Scenario 1: Login successful");
        } else {
            System.out.println("Scenario 1: Login failed");
        }

        // Log out
        logout(driver);

        // Verify user is navigated to login page
        if (driver.getCurrentUrl().equals("https://www.saucedemo.com/")) {
            System.out.println("Scenario 1: Logout successful");
        } else {
            System.out.println("Scenario 1: Logout failed");
        }
        
        // Scenario 2: Log in using locked out user
        login(driver, "locked_out_user", "secret_sauce");

        // Verify error message
        WebElement errorMessage = driver.findElement(By.cssSelector("[data-test='error']"));
        if (errorMessage.getText().equals("Epic sadface: Sorry, this user has been locked out.")) {
            System.out.println("Scenario 2: Error message verified");
        } else {
            System.out.println("Scenario 2: Error message not found or incorrect");
        }
        

    }

    public static void login(WebDriver driver, String username, String password) 
    {
        // Open the website
        driver.get("https://www.saucedemo.com/");

        // Find the username and password fields and enter the credentials
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        // Click the login button
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // Wait for the inventory page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/inventory.html"));
        } 
        
        catch (TimeoutException e) 
        {
        	
        }
        
        // Delay on inventory page
        try {
            Thread.sleep(5000); // 5 seconds delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
        
    public static void logout(WebDriver driver) 
    {
        // Click the menu button
        WebElement menuButton = driver.findElement(By.cssSelector(".bm-burger-button"));
        menuButton.click();

        // Click the logout button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        logoutButton.click();

        // Wait for the login page to load
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/"));
    }
}