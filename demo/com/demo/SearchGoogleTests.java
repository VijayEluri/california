package com.demo;

import com.tomkp.california.CaliforniaRunner;
import com.tomkp.california.annotations.California;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CaliforniaRunner.class)
@California(
        suiteName = "Demo Tests",
        basePackage = "com.demo",
        suffix = ".spec",
        specsPath = "demo/specs"
)
public class SearchGoogleTests {


//    public static TestSuite suite() {
//
//        return generateSuite("Demo Tests", "com.demo", ".spec", "demo/specs");
//
//    }

    //@Test
    public void x() {

    }


    public void testX() {

    }
}
