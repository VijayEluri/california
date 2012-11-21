package com.demo;

import com.tomkp.california.CaliforniaSuite;
import junit.framework.TestSuite;

public class DemoSuite extends CaliforniaSuite  {



    public static TestSuite suite() {

        return generateSuite("Demo Tests", "com.demo", ".spec", "demo/specs");

    }

}
