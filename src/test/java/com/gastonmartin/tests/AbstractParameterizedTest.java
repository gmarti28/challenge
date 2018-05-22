package com.gastonmartin.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

/**
 * Esta clase sirve para crear tests parametrizados. Al extender de esta clase se puede especificar el browser
 * especificando el parametro browser  ("chrome" "firefox" "internetexplorer" "edge")
 *
 */
public abstract class AbstractParameterizedTest {

    protected static WebDriver driver;

    @Parameters({"browser"})
    @BeforeClass
    public static void setupBrowser(@Optional("chrome") String browser) {
        String browserName=(""+browser).toLowerCase();
        switch (browserName){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(new ChromeOptions().addArguments("disable-infobars"));
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "internetexplorer":
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                break;
            case "opera":
                WebDriverManager.operadriver().setup();
                driver = new OperaDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Invalid browser name: '" + browser +"'");
        }
        if (driver != null) {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();

        }
    }

    @AfterClass
    public void quitDriver(){
        if (driver != null) {
            driver.quit(); //Closes all browser windows and safely ends the session
        }
    }
}
