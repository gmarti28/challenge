package com.gastonmartin.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ChallengePage {

    private static final String URL="http://exercises.fdvs.com.ar/senior.html";
    private final WebDriver driver;

    @FindBy(how = How.ID, using = "checkresults")
    private WebElement checkResults;

    @FindBy(how = How.ID, using = "name")
    private WebElement name;

    @FindBy(how = How.ID, using="occupation")
    private WebElement occupation;

    /* Answer Slots */
    @FindBy(how = How.ID, using = "answer1")
    private WebElement answer1; //Title of page
    @FindBy(how = How.ID, using = "answer4")
    private WebElement answer4; //Number of black boxes?:
    @FindBy(how = How.ID, using = "answer6")
    private WebElement answer6; //Class of red box?:
    @FindBy(how = How.ID, using = "answer8")
    private WebElement answer8; //Text from the red box
    @FindBy(how = How.ID, using = "answer9")
    private WebElement answer9; //Which box is on top? Orange or Green
    @FindBy(how = How.ID, using = "answer10")
    private WebElement answer10; //Is an item with id of IAmHere present on this page?
    @FindBy(how = How.ID, using = "answer11")
    private WebElement answer11; //Is an item with id of purplebox visible on this page?
    @FindBy(how = How.ID, using = "answer12")
    private WebElement answer12; //Value of return from got_return_from_js_function?


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

    public void setName(String t){
        name.sendKeys(""+t);
    }

    public void setOccupationByText(String text){
        Select s = new Select(occupation);
        s.selectByVisibleText(text);
    }

    public void setOccupationByValue(String value){
        Select s = new Select(occupation);
        s.selectByValue(value);
    }

    public long countBlackBoxes(){
        /* Just to show off I can handle Java 8 ;-)
         * Also I'd like to avoid miscalculating black boxes based just on classname */
        return driver.findElements(By.className("blackbox"))
               .stream()
               .filter(e->{ return e.getText().equalsIgnoreCase("Black Box"); })
               .count();
    }
    public void setAnswer1(String answer){
        answer1.sendKeys(""+answer);
    }

    public void setAnswer4(String answer){
        answer4.sendKeys(""+answer);
    }

    public void setAnswer6(String answer){
        answer6.sendKeys(""+answer);
    }

    public void setAnswer8(String answer){
        answer8.sendKeys(""+answer);
    }

    public void setAnswer9(String answer){
        answer9.sendKeys(""+answer);
    }
    public void setAnswer10(String answer){
        answer10.sendKeys(""+answer);
    }
    public void setAnswer11(String answer){
        answer11.sendKeys(""+answer);
    }
    public void setAnswer12(String answer){
        answer12.sendKeys(""+answer);
    }

    public String getTitle(){
        return ""+driver.getTitle();
    }





    public void waitPageIsLoaded(int timeoutInSeconds){
        new WebDriverWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.visibilityOf(lastElementOfPage));
    }

    public void waitPageIsLoaded(){
        waitPageIsLoaded(10); //Default timeout in seconds.
    }
}
