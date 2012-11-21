# California

  Run cucumber tests in junit using java annotations


## Examples



```
Feature: Google Tests

    Scenario: search google
        Given I am on 'http://www.google.com'
        And I search for 'cucumber'
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


    @Step("I search for '(.*)'")
    public void searchFor(String text) throws Exception {
        driver.findElement(By.id("gbqfq")).sendKeys(text, Keys.RETURN);
        Thread.sleep(1000);
    }


    @Step("I should see (.*) results")
    public void shouldSeeResults(Integer numberOfResults) {
        Integer actual = driver.findElements(By.className("r")).size();
        assertEquals(numberOfResults, actual);
    }

}


```
