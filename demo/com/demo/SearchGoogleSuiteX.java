package com.demo;

import com.tomkp.california.CaliforniaSuite;
import com.tomkp.california.CaliforniaSuiteX;
import junit.framework.TestSuite;

public class SearchGoogleSuiteX extends CaliforniaSuiteX {



    public static TestSuite suite() {

        return generateSuite("Demo Tests", "com.demo", ".spec", "demo/specs");

    }

}
