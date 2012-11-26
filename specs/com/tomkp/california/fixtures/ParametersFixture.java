package com.tomkp.california.fixtures;

import com.tomkp.california.annotations.Fixture;
import com.tomkp.california.annotations.Step;
import org.apache.log4j.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Date;

@Fixture
public class ParametersFixture {


    private static final Logger LOG = Logger.getLogger(ParametersFixture.class);


    @Step(value="the list '(.*)' has a size of (.*)", formats={",", ""})
    public void listParameter(List list, Integer size) throws Exception {
        System.out.println("list: " + list + " " + list.size());
        for (Object o : list) {
            System.out.println("o: " + o);
        }
        System.out.println("size: " + size);
        assertThat(list.size(), equalTo(size));
    }


    @Step("the list '(.*)' contains sub list '(.*)'")
    public void listParameters(List list, List subList) throws Exception {
        assertTrue(list.containsAll(subList));
    }


    @Step("integer parameter : '(.*)'")
    public void integerParameter(Integer integer) throws Exception {
        LOG.info("integer: '" + integer + "'");
    }

    @Step("string parameter : '(.*)'")
    public void stringParameter(String string) throws Exception {
        LOG.info("string: '" + string + "'");
    }

    @Step("float parameter : '(.*)'")
    public void floatParameter(Float aFloat) throws Exception {
        LOG.info("aFloat: '" + aFloat + "'");
    }

    @Step("double parameter : '(.*)'")
    public void doubleParameter(Double aDouble) throws Exception {
        LOG.info("aDouble: '" + aDouble + "'");
    }

    @Step(value = "date parameter : '(.*)'", formats ="dd MMM yyyy")
    public void dateParameter(Date date) throws Exception {
        LOG.info("date: '" + date + "'");
    }

    @Step(value = "boolean parameter : '(.*)'")
    public void booleanParameter(Boolean aBoolean) throws Exception {
        LOG.info("aBoolean: '" + aBoolean + "'");
    }
}
