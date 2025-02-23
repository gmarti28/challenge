# A Challenge using Spring Security

## Selected technology stack

These are the technologies chosen for this project

* Apache Maven (https://maven.apache.org/)
* Java 8 (https://www.oracle.com/java/technologies/java-se.html) *Althought Java reached its version 10, project compiles and runs well using JDK 8 or later*. 
* TestNG (http://testng.org/doc/)
* Selenium Webdriver (http://www.seleniumhq.org/projects/webdriver/)
* WebDriverManager (https://github.com/bonigarcia/webdrivermanager)

Project created with IntelliJ IDEA 2018.1.3 (Community Edition) https://www.jetbrains.com/idea


## Execution 

 * set **JAVA_HOME**
 * run **mvn test**
 
Specific browser to be used can be specified adding 

	-Dbrowser=browsername

Available browsers:

- *chrome*  (default)
- *firefox*
- *internetexplorer*
- *opera*
- *edge* 

Example:
 
	mvn test -Dbrowser=firefox
 
 **Notice some browsers might not be available in your operating system**
 
## Patterns used and decisions made

### Decision of quitting browser after test end

Despite the exercise conveys to fulfill a series of steps by means of automation and **leave the browser open** so the results can be finally validated by direct observation, the whole process got automated leaving an **arbitrary sleep** of **8 seconds** at the end of the exercise before execution ends and closes the browser. 
So any human observer could watch it run without leaving any browser open as a "loose end". 

This decision intends to fulfill a very common "*best practice*" of **closing all resources** before ending. 

### Decision about using Soft Assertions

Although the excersise doesn't tell to use any assertion at all, there were added 16 of them at the end of the test. So each step taken is checked for "OK" "NOT OK" on screen,  producing a test failure if any single assertion failed. 

It wasn't clear if exercise aimed to evaluate **just** my **Selenium skills** or my whole philosophy about automation subject (along with every choice needed to come up with a testing solution). So I decided to add some extra elements out of my personal taste. 

Bearing that in mind I decided to go with just one test method for the sake of simplicity, having all statements taken care of, but including a final assert at the end. In this particular context, steps seemed to be intertwined enough to put them all in the same test method. 

Provided that TestNG guarantees execution order of tests I would have put every single one of them into a separate method within its own formal chceck but it turns out that assertions cannot be made without clicking "Check results" button which is shared among all steps. 
At the end the day it's just an exercise and not a real world case, so I just ended up choosing a fair trade-off between test isolation and good practice demonstration putting all assertions together. 

As an experienced automation tester I painfully know the drawbacks of stating all assertions into the same test flow within the same test method: if any single assertion fails it would prevent the execution of the remaining code, even if subsequent checks are not dependant on the failed assertion thus leading to lack of awareness to other features failing.
 
In order to overcome that I decided to use TestNG's Soft Assertion, grouping and evaluating it all together, so every failed assertion would get noticed. 

>For more information about TestNG Soft Assert refer to [SoftAssert API Doc](https://jitpack.io/com/github/cbeust/testng/master/javadoc/org/testng/asserts/SoftAssert.html) 
 
### Page Object Pattern

Specification of tests (what to test) could -and should- be separated from implementation of tests (how to test) by encapsulating knowledge of the application under test to an intermediate API layer. Thus hiding implementation details such as how to locate a specific HTML element of the application and letting a test client interact with the API methods without knowing any single implementation detail(hence decoupling tests from always changing application).

This practice which is often called "**Page Object** design pattern" by people doing automation with Seleniun became popular over the last years as Selenium were adopted as a state of the art tool for automating web and UI. 

The main concern of Page Objects is to reduce maintenance of tests by concentrating all the implementation details withing the Page Object class. So any change in the application will require only a change in the Page Object instead of fixing dozens of tests. 

As a consequence of moving implementation details into the Page Object class we will get a a pretty neat specification for our test having just to call the Page Object methods to get and set data into the application, or invoking some operation (such as submiting a form) 

These are the generally accepted rules for a Page Object:

 * The **public** methods of the PO represents the **services** that the page offers.
 * The page object **internally knows** how to locate elements and interact with the AUT
 * A page object does not necessarily need to represent an **entire page**.
 * **Assertions** should be left **out of the Page Object** and be always within the test code.
 * Methods generally **return other Page Objects**, so different results for the same action should be modelled as different methods. 
 * Returning a PageObject allows to **method chaining**  such as: 
 
		page.loadPage()
		    .setAnswer1(page.getTitle())
		    .setName(page.getDesiredNameForStep2()); 

 
 More info on Page Object pattern:
 * https://docs.seleniumhq.org/docs/06_test_design_considerations.jsp#page-object-design-pattern
 * https://martinfowler.com/bliki/PageObject.html
 
### PageFactory pattern

PageFactory pattern allows to instantiate the PageObject and initialize each field annotated with @FindBy annotation making the page object even neater: 

    @FindBy(how = How.CSS, using = "span#redbox")
    private WebElement redbox;

Then using org.openqa.selenium.support.PageFactory it can be instantiated as 

	page = PageFactory.initElements(driver,ChallengePage.class);

Any page object method returning a different page object should initialize it before returning it back by this approach: 

	return PageFactory.initElements(driver, AnotherPage.class); 
	
It's not unusual to provide a static "navigateTo" initializer: 

	public static ChallengePage navigateTo (WebDriver driver) {
		driver.get("http://exercises.fdvs.com.ar/senior.html");
		return PageFactory.initElements(driver, ChallengePage.class);
	}

## Java 8 idioms

In the **countBlackBoxes()**  method I've used Java8 stream api to filter out all HTML elements with blackbox class and inner text matching "Black Box". In this particular idiom I used a lambda expression as a filter for the stream and finally a count() collector. 

	 public long countBlackBoxes(){
	        return driver.findElements(By.className("blackbox"))
	               .stream()
	               .filter(e->{ return e.getText().equalsIgnoreCase("Black Box"); })
	               .count();
	    }
    
In **clickLinksWithText()** a *forEach* was used to perform the click() action on each link found.

    public ChallengePage clickLinksWithText(String text){
        driver.findElements(By.linkText(text)).forEach( e->{
            e.click();
        });
        return this;
    }

In **getTopmostBoxColor() ** I wanted to pick within greenbox and orangebox the element with the least Y coordinate to figure out which element got rendered before in the page. So calling for min() with a lambda comparator of e-> e.getLocation().getY() got the trick done and stripped the "box" out of the element's id afterwards. 

	     public String getTopmostBoxColor(){
	        return  driver.findElements(By.cssSelector("span#greenbox,span#orangebox"))
	                .stream()
	                .min(Comparator.comparing( e-> e.getLocation().getY()))
	                .orElseThrow(java.util.NoSuchElementException::new)
	                .getAttribute("id").replace("box","");
	    }
	
Lastly, in the main test method I've generated a range of integers from 1 to 16 with IntStream.range(1, 16) and interated over them adding a soft assertion on each one. I personally find this way clearer than an old school for-loop but that is *just a matter of taste*. 

        IntStream.range(1, 16)
                .forEach( n->{
                    soft.assertEquals(page.getResult(n), "OK","Result #"+n+" was not ok.");
                });
                
## Last words

I've tried to demonstrate some of my broader set of skills to make the most of this challenge excercise. However, in a real life scenario not all strategies might apply, or it could have been necessary to take a whole different approach. 

Over the last years I struggled with fast changing projects, switching from one programming language to another and so with frameworks and tools. Even with no prior knowledge of language or tool adopted. 

I've had to learn to test with Java, JUnit, TestNG, RestAssured, HamCrest, javascript, es6, jasmine, protractor, artillery, scala, scalatest, AssertJ-DB, even with python and unittest. Had to pile-up such technologies to work together, also with deployment technologies such as Jenkins and Docker, and databases such as MySQL, MariaDB, and postgreSQL. Every single one proved to be a challenge and I've learn a bit of everything along the way. Or at least that is what I expect so. 

I had fun facing this challenge and hope to get some feedback and keep in touch. 

Sincerely regards,
Gaston Martin
gastonm@gmail.com
