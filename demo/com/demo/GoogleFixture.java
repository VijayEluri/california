package com.demo;

import com.tomkp.california.annotations.AfterScenarios;
import com.tomkp.california.annotations.BeforeScenarios;
import com.tomkp.california.annotations.Fixture;
import com.tomkp.california.annotations.Step;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;


@Fixture
public class GoogleFixture {

    private static final Logger LOG = Logger.getLogger(GoogleFixture.class);

    private WebDriver driver = null;


    @BeforeScenarios("open the browser")
    public void openBrowser() {
        System.out.println("openBrowser >>>>");
        if (LOG.isInfoEnabled()) LOG.info("open browser");
        if (driver == null) {
            driver = new FirefoxDriver();
        }
        System.out.println("<<<< openBrowser");
    }


    @AfterScenarios("quit the browser")
    public void closeBrowser() {
        System.out.println("closeBrowser >>>>");
        if (LOG.isInfoEnabled()) LOG.info("close browser");
        driver.close();
        System.out.println("<<<< closeBrowser");
    }


    @Step("I am on '(.*)'")
    public void onThePage(String page) {
        System.out.println("onThePage >>>>");
        if (LOG.isInfoEnabled()) LOG.info("page: '" + page + "'");
        driver.get(page);
        System.out.println("<<<< onThePage");
    }


    @Step("I search for '(.*)'")
    public void searchFor(String text) throws Exception {
        System.out.println("type >>>>");
        if (LOG.isInfoEnabled()) LOG.info("text: '" + text + "'");
        driver.findElement(By.id("gbqfq")).sendKeys(text, Keys.RETURN);
        Thread.sleep(1000);
        System.out.println("<<<< type");
    }


    @Step("I should see (.*) results")
    public void shouldSeeResults(Integer numberOfResults) {
        System.out.println("shouldSeeResults >>>>");
        if (LOG.isInfoEnabled()) LOG.info("numberOfResults: '" + numberOfResults + "'");
        Integer actual = driver.findElements(By.className("r")).size();
        assertEquals(numberOfResults, actual);
        System.out.println("<<<< shouldSeeResults");
    }


}
