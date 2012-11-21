# California

  Run cucumber tests in junit using java annotations


## Examples



```
Feature: Google Tests

    Scenario: search google
        Given I am on 'http://www.google.com'
        And I type 'cucumber'
        Then I should see 10 results

```



```java


@Fixture
public class GoogleFixture {


    private WebDriver driver = null;


    @BeforeScenarios("open the browser")
    public void openBrowser() {
        if (driver == null) {
            driver = new FirefoxDriver();
        }
    }


    @AfterScenarios("quit the browser")
    public void closeBrowser() {
        driver.close();
    }


    @Step("I am on '(.*)'")
    public void onThePage(String page) {
        driver.get(page);
    }


    @Step("I type '(.*)'")
    public void type(String text) {
        driver.findElement(By.id("gbqfq")).sendKeys(text);
    }


    @Step("I click '(.*)'")
    public void click(String text) {
        driver.findElement(By.id("gbqfba")).click();
    }


    @Step("I should see (.*) results")
    public void shouldSeeResults(Integer numberOfResults) {
        System.out.println("numberOfResults");
    }

}


```
