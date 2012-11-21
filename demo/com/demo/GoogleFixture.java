package com.demo;

import com.tomkp.california.annotations.AfterScenarios;
import com.tomkp.california.annotations.BeforeScenarios;
import com.tomkp.california.annotations.Fixture;
import com.tomkp.california.annotations.Step;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


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


    @Step("I type '(.*)'")
    public void type(String text) {
        System.out.println("type >>>>");
        if (LOG.isInfoEnabled()) LOG.info("text: '" + text + "'");
        driver.findElement(By.id("gbqfq")).sendKeys(text);
        System.out.println("<<<< type");
    }


    @Step("I click '(.*)'")
    public void click(String text) {
        System.out.println("click >>>>");
        if (LOG.isInfoEnabled()) LOG.info("text: '" + text + "'");
        driver.findElement(By.id("gbqfba")).click();
        System.out.println("<<<< click");
    }


    @Step("I should see (.*) results")
    public void shouldSeeResults(Integer numberOfResults) {
        System.out.println("shouldSeeResults >>>>");
        if (LOG.isInfoEnabled()) LOG.info("numberOfResults: '" + numberOfResults + "'");
        System.out.println("<<<< shouldSeeResults");
    }


}
