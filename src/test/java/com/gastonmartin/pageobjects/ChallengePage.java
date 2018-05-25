package com.gastonmartin.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.Comparator;


public class ChallengePage {

    private static final String URL="http://exercises.fdvs.com.ar/senior.html";
    private final WebDriver driver;

    @FindBy(how = How.ID, using = "checkresults")
    private WebElement checkResults;

    @FindBy(how = How.ID, using = "name")
    private WebElement name;

    @FindBy(how = How.ID, using="occupation")
    private WebElement occupation;

    @FindBy(how = How.CSS, using = "span#redbox")
    private WebElement redbox;

    private final String purpleboxID="purplebox"; // I need to use this also for last element
    @FindBy(how = How.ID, using = purpleboxID)
    private WebElement purplebox;


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

    public void setPositionByText(String text){
        driver.findElement(By.cssSelector("input[name='position'][value='" + text + "']"))
        .click();
    }

    public long countBlackBoxes(){
        /* Just to show off I can handle Java 8 ;-)
         * Also I'd like to avoid miscalculating black boxes based just on classname */
        return driver.findElements(By.className("blackbox"))
               .stream()
               .filter(e->{ return e.getText().equalsIgnoreCase("Black Box"); })
               .count();
    }

    public String getRedboxClass(){
        return ""+redbox.getAttribute("class");
    }

    public String getRedboxText(){
        return ""+redbox.getText();
    }

    public boolean isItemPresentWithID(String id){
        // Please notice this search is affected by driver's implicit wait
        return  driver.findElements(By.id(id)).size() > 0;
    }

    public boolean isPurpleBoxVisible(){
        return purplebox.isDisplayed();
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

    public void clickLinksWithText(String text){
        // In case more than one link is present with same text, all of them will get clicked. :0
        driver.findElements(By.linkText(text)).forEach( e->{
            e.click();
        });
    }

    public String getTitle(){
        return ""+driver.getTitle();
    }

    public String getDesiredNameForStep2(){
        return driver.findElement(By.cssSelector("li > span#ok_2 + i + b")).getText();
    }
    public String getDesiredOccupationForStep3(){
        return driver.findElement(By.cssSelector("li > span#ok_3 + b")).getText();
    }

    public String getDesiredLinkTextToClickForStep5(){
        return driver.findElement(By.cssSelector("li > span#ok_5 + b")).getText();
    }

    public String getDesiredPositionForStep7(){
        return driver.findElement(By.cssSelector("li > span#ok_7 + b")).getText();
    }

     public String getTopmostBoxColor(){
        return  driver.findElements(By.cssSelector("span#greenbox,span#orangebox"))
                .stream()
                .min(Comparator.comparing( e-> e.getLocation().getY()))
                .orElseThrow(java.util.NoSuchElementException::new)
                .getAttribute("id").replace("box","");
    }

    /* Waits */

    public void waitPageIsLoaded(){
        waitPageIsLoaded(10); //Default timeout in seconds.
    }

    public void waitPageIsLoaded(int timeoutInSeconds){
        /* Given the very last element of page is the purplebox, which is not always visible
         * the expectation visibilityOf cannot be used and so I had to switch to
         * presenceOfElementLocated(By locator) instead. */
        new WebDriverWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.presenceOfElementLocated(By.id(purpleboxID)));
    }

    public void waitLinkAndClickWithLinkText(String linkText, long timeOutInSeconds, long pollingInterval){
        waitElementAndClick(By.linkText(linkText),timeOutInSeconds, pollingInterval);
    }

    public void waitElementAndClick(By locator, long timeOutInSeconds, long pollingInterval){

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeOutInSeconds))
                .pollingEvery(Duration.ofMillis(pollingInterval))
                .ignoring(org.openqa.selenium.NoSuchElementException.class);

        wait.until(ExpectedConditions.elementToBeClickable(locator))
                .click();
    }

    public Alert waitAlert(long waitSeconds, long pollingIntervalInMillis ){
        WebDriverWait wait = new WebDriverWait(driver,waitSeconds, pollingIntervalInMillis);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        return alert;
    }
}
