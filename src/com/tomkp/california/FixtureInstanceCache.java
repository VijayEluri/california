package com.tomkp.california;

import com.tomkp.california.annotations.Fixture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class FixtureInstanceCache {


    private static final Logger LOG = LoggerFactory.getLogger(FixtureInstanceCache.class);


    private Cache fullSuiteCache = new Cache();
    private CacheMap<Scenario> scenarioCache = new CacheMap<Scenario>();
    private CacheMap<Feature> featureCache = new CacheMap<Feature>();


    @SuppressWarnings("unchecked")
    public Object getInstance(Line line, Class clas) throws InstantiationException, IllegalAccessException {
        Fixture annotation = (Fixture)clas.getAnnotation(Fixture.class);
        Lifespan lifespan = annotation.lifespan();
        Object instance;
        if (lifespan == Lifespan.FULL_SUITE) {
            instance = fullSuiteCache.getInstance(clas);
        } else if (lifespan == Lifespan.FEATURE) {
            Feature feature = line.getScenario().getFeature();
            instance = featureCache.cache(clas, feature);
        } else if (lifespan == Lifespan.SCENARIO) {
            Scenario scenario = line.getScenario();
            instance = scenarioCache.cache(clas, scenario);
        } else {
            LOG.info("create new instance of '{}'", clas);
            instance = clas.newInstance();
        }
        return instance;
    }


    private class CacheMap<K> {

        private Map<K, Cache> cacheMap = new HashMap<K, Cache>();

        public Object cache(Class clas, K key) throws InstantiationException, IllegalAccessException {
            Object instance;
            if (cacheMap.containsKey(key)) {
                instance = cacheMap.get(key).getInstance(clas);
            } else {
                Cache cache = new Cache();
                instance = cache.getInstance(clas);
                cacheMap.put(key, cache);
            }
            return instance;
        }

    }


    private class Cache {

        private Map<Class, Object> instances = new HashMap<Class, Object>();

        public Object getInstance(Class clas) throws InstantiationException, IllegalAccessException {
            Object instance;
            if (instances.containsKey(clas)) {
                instance = instances.get(clas);
                LOG.info("use instance '{}' from cache", instance);
            } else {
                LOG.info("create new instance of '{}'", clas);
                instance = clas.newInstance();
                LOG.info("store instance '{}' in cache", instance);
                instances.put(clas, instance);
            }
            return instance;
        }

    }

}
