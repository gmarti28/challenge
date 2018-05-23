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

        // Grab Page Title and place title text in Answer Slot #1
        page.setAnswer1(page.getTitle());

        // Fill out name section of form to be Mariano Arcelus
        // Set occupation on form to Scrum Master
        // Count number of black boxes on page after form and enter into Answer Slot #4
        // Click link that says 'Click Me'
        // Find red box on its page find class applied to it, and enter into Answer Slot #6
        // Mark radio button on form for position ? to Tech-Lead
        // Get the text from the Red Box and place it in Answer Slot #8
        // Which box is on top? orange or green -- place correct background color in Answer Slot #9
        // Type into Answer Slot #10 YES or NO depending on whether item by ID of IAmHere is on the page
        // Type into Answer Slot #11 YES or NO depending on whether item with ID of purplebox is visible
        // Click the link with text 'Wait'. A random wait will occur and then a new link will be added with text 'Click After Wait'. Click this new link within 500 ms to pass this test
        // Click OK on the Confirm Alert after completing Task 12.
        // Run JavaScript function as: ran_this_js_function() from your Selenium script
        // Run JavaScript function as: got_return_from_js_function() from your Selenium script, take returned value and place it in Answer Slot #12
        // Click the Submit button on the form

        // Aesthetic wait to allow last action to be noticed by human observer ;)

        page.doCheckResults();
        Thread.sleep(8000);

    }
}
