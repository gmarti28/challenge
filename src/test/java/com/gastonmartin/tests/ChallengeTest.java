package com.gastonmartin.tests;


import com.gastonmartin.pageobjects.ChallengePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class ChallengeTest extends AbstractParameterizedTest{

    /* Parent class holds the logic for handling WebDriverManager browser setup
     * Default browser is Google Chrome but others are available when parameter "browser" is properly set */


    /* PageObject members */
    ChallengePage page;

    @BeforeMethod
    public void init(){
        // I could have used this init statement inside PageObejct own's constructor instead.
        page = PageFactory.initElements(driver,ChallengePage.class);
    }
    @Test
    public void doChallenge() throws InterruptedException {

        // Navigate to Challenge page
        page.loadPage();

        // 1. Grab Page Title and place title text in Answer Slot #1
        page.setAnswer1(page.getTitle());

        // 2. Fill out name section of form to be whatever step 2 states.
        page.setName(page.getDesiredNameForStep2());

        // 3. Set occupation on form to whatever step 3 says so
        String chosenOccupation=page.getDesiredOccupationForStep3();
        page.setOccupationByText(chosenOccupation);
        // Could have used page.setOccupationByValue("sm"); also
        // (Useful if labels are language-specific) but step 3 randomly changes when page is loaded

        // 4. Count number of black boxes on page after form and enter into Answer Slot #4
        page.setAnswer4(Long.toString(page.countBlackBoxes()));

        // 5. Click link that says ... (whatever page stated)
        page.clickLinksWithText(page.getDesiredLinkTextToClickForStep5());

        // 6. Find red box on its page find class applied to it, and enter into Answer Slot #6
        page.setAnswer6(page.getRedboxClass());

        // 7. Mark radio button on form for position
        page.setPositionByText(page.getDesiredPositionForStep7());

        // 8. Get the text from the Red Box and place it in Answer Slot #8
        page.setAnswer8(page.getRedboxText());

        // 9. Which box is on top? orange or green -- place correct background color in Answer Slot #9
        page.setAnswer9(page.getTopmostBoxColor());

        // 10. Type into Answer Slot #10 YES or NO depending on whether item by ID of IAmHere is on the page
        page.setAnswer10(page.isItemPresentWithID("IAmHere")? "YES" : "NO");

        // 11. Type into Answer Slot #11 YES or NO depending on whether item with ID of purplebox is visible
        page.setAnswer11(page.isPurpleBoxVisible()? "YES" : "NO");

        // 12. Click the link with text 'Wait'. A random wait will occur and then a new link will be added with text 'Click After Wait'. Click this new link within 500 ms to pass this test
        page.clickLinksWithText("Wait"); // I could have borrowed the expected text from its statement as I did for steps #2, #3 and #5
        page.waitLinkAndClickWithLinkText("Click After Wait", 10,100);

        // 13. Click OK on the Confirm Alert after completing Task 12.
        page.waitAlert(5,100).accept();

        // 14. Run JavaScript function as: ran_this_js_function() from your Selenium script
        page.runScript("ran_this_js_function");

        // 15. Run JavaScript function as: got_return_from_js_function() from your Selenium script, take returned value and place it in Answer Slot #12
        page.setAnswer12(""+page.runScript("got_return_from_js_function"));
        // 16. Click the Submit button on the form
        

        page.doCheckResults();

        // Aesthetic wait to allow last action to be noticed by human observer ;)
        Thread.sleep(8000);

    }
}
