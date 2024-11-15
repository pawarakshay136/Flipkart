package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import demo.wrappers.Wrappers;



import demo.wrappers.Wrappers;
//import dev.failsafe.internal.util.Assert;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);


        driver.manage().window().maximize();
    }
     
    @Test
    public void testCase01() throws InterruptedException{
        System.out.println("Starting testCase01");
        double starRating = 4.0;
        driver.get("https://www.flipkart.com/");
        Wrappers.flipkartSeacrchBar(driver, By.xpath("//input[@title='Search for Products, Brands and More']"), "Washing Machine");
        Thread.sleep(3000);
        Wrappers.clickOnElementWrapper(driver, By.xpath("//div[text()='Popularity']"));
        Thread.sleep(5000);
        Boolean status = Wrappers.searchRatingAndCount(driver, By.xpath("//span[contains(@id,'productRating')]/div"), starRating);
        Assert.assertTrue(status);
        System.out.println("Tescase01 Completed");
    }


    @Test
    public void testCase02() throws InterruptedException{
        System.out.println("Starting testCase02");
        int discount = 17;
        driver.get("https://www.flipkart.com/");
        Wrappers.flipkartSeacrchBar(driver, By.xpath("//input[@title='Search for Products, Brands and More']"), "iPhone");
        Thread.sleep(3000);
        Boolean status = Wrappers.titleAndDiscount(driver, By.xpath("//div[contains(@class,'yKfJKb')]"),discount);
        Assert.assertTrue(status);
        System.out.println("Ending Test Case 02");

    }

    @Test
    public void testCase03() throws InterruptedException{
     System.out.println("test case03 started");
     driver.get("https://www.flipkart.com/");
     Wrappers.flipkartSeacrchBar(driver, By.xpath("//input[@title='Search for Products, Brands and More']"), "Coffee Mug");
     Thread.sleep(3500);
     Wrappers.clickOnElementWrapper(driver, By.xpath("//div[contains(text(),'4★ & above')]"));
     Thread.sleep(3500);
     Boolean status = Wrappers.printTitleandImageURL(driver,  By.xpath("//div[@class='slAVV4']//span[@class='Wphh3N']"));
 
     Assert.assertTrue(status);
     System.out.println("test case 3 completed");
 
    } 

   
    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}