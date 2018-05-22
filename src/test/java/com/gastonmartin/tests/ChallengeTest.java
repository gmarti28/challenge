package com.gastonmartin.tests;


import com.gastonmartin.pageobjects.ChallengePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class ChallengeTest extends AbstractParameterizedTest{

    /* Parent class holds the logic for handling WebDriverManager browser setup
     * Default browser is Google Chrome but others are available when parameter "browser" is properly set */


    /* PageObject members */
    ChallengePage page;

    @BeforeMethod
    public void init(){
        page = PageFactory.initElements(driver,ChallengePage.class);
        System.out.println("BeforeMethod");
    }
    @Test
    public void doChallenge() throws InterruptedException {

        System.out.println("doChallenge");
        // Navigate to google search page
        page.loadPage();
        page.doCheckResults();

        // Aesthetic wait to allow last action to be noticed by human observer ;)
        Thread.sleep(2000);

    }
}
