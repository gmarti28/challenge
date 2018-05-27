# Challenge resolution

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

Despite the exercise conveys to fulfill a series of steps by means of automation and **leave the browser open** so the results can be finally checked by direct observation, the whole process got automated leaving an **arbitrary sleep** of **8 seconds** at the end of the exercise before execution ends and closes the browser. 
So any human observer could watch it run without leaving any browser open as a "loose end". 

This decision intends to fulfill a very common "*best practice*" of **closing all resources** before ending. 

### Decision about using Soft Assertions

Although the excersise doesn't tell to use any assertion at all, there were added 16 of them at the end of the test. So each step taken is checked for "OK" "NOT OK" on screen,  producing a test failure if any single assertion failed. 

It wasn't clear if exercise aimed to evaluate **just** my **Selenium skills** or my whole philosophy about automation subject (along with every choice needed to come up with a testing solution). So I decided to add some extra elements out of my personal taste. 

Bearing that in mind I decided to go with just one test method for the sake of simplicity, having all statements taken care of, but including a final assert at the end. In this particular context, steps seemed to be intertwined enough to put them all in the very same test method. 

Provided that TestNG guarantees execution order of tests I would have put every single one of them into a separate method within its own formal chceck but it turns out that assertions cannot be made without clicking "Check results" button which is shared among all steps. 
At the end the day it's just an exercise and not a real world case, so I just ended up chosing a fair trade-off between test isolation and good practice demonstration putting all assertions together. 

As an experienced automation tester I painfully know the drawbacks of stating all assertions into the same test flow within the same test method: if any single assertion fails it would prevent the execution of the remaining ones, even if subsequent checks are not dependant on the failed assertion thus leading to lack of awareness to other features failing.
 
In order to overcome that I decided to use TestNG's Soft Assertion, grouping and evaluating it all together, so every failed assertion would get noticed. 

>For more information about TestNG Soft Assert refer to [SoftAssert API Doc](https://jitpack.io/com/github/cbeust/testng/master/javadoc/org/testng/asserts/SoftAssert.html) 
 
