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