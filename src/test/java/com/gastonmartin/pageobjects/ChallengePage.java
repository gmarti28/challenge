package com.gastonmartin.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ChallengePage {

    private static final String URL="http://exercises.fdvs.com.ar/senior.html";
    private final WebDriver driver;

    @FindBy(how = How.ID, using = "checkresults")
    private WebElement checkResults;

    //todo: Add WebElements with their Locators

    @FindBy(how = How.ID, using="answer12") //todo: replace this with reference to last Element
    private WebElement lastElementOfPage;


    public ChallengePage(WebDriver driver) {
        this.driver=driver;
    }

    public void loadPage(){
        driver.get(URL);
        waitPageIsLoaded();
    }

    public void doCheckResults(){
        checkResults.click();
    }

    public void waitPageIsLoaded(int timeoutInSeconds){
        new WebDriverWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.visibilityOf(lastElementOfPage));
    }

    public void waitPageIsLoaded(){
        waitPageIsLoaded(10); //Default timeout in seconds.
    }
}
