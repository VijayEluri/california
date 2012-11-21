package com.tomkp.california.classes;

import com.tomkp.california.annotations.Fixture;
import com.tomkp.california.annotations.Step;

@Fixture
public class AnnotatedWithFixtureAndStep {

    @Step("one")
    public void method1() {

    }


    @Step("two")
    public void method2() {

    }
}