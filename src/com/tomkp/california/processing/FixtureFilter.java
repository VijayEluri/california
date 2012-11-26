package com.tomkp.california.processing;


import com.tomkp.california.annotations.Fixture;

public class FixtureFilter implements ClassFilter {

    @Override
    public boolean filter(Class clas) {
        return clas.isAnnotationPresent(Fixture.class);
    }

}
