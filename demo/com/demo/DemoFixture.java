package com.demo;

import com.tomkp.california.annotations.Fixture;
import com.tomkp.california.annotations.Step;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@Fixture
public class DemoFixture {

    private static final Logger LOG = Logger.getLogger(DemoFixture.class);


    @Step("login as '(.*)'")
    public void login(String name, String password) {
        if (LOG.isInfoEnabled()) LOG.info("name: '" + name + "'");
    }


    @Step(value= "set date as '(.*)'", formats = {"dd MMM yyyy"})
    public void x(Date date) {
        if (LOG.isInfoEnabled()) LOG.info("date: '" + date + "'");
    }


    @Step(value="hello '(.*)'", formats = {" "})
    public void login(List<String> name) {
        for (String s : name) {
            System.out.println("s: " + s);
        }
    }


    @Step(value="page is '(.*)'")
    public void pageIs(String name) {
        String page = "x";
        assertThat(name, equalTo(page));
    }
}
