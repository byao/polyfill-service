package org.polyfill.api.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.polyfill.api.components.Polyfill;
import org.polyfill.api.configurations.MockPolyfillsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by smo on 2/26/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader=AnnotationConfigContextLoader.class,
        classes={MockPolyfillsConfig.class,
                JsonConfigLoaderService.class,
                FinancialTimesPolyfillLoaderService.class
        })
public class FinancialTimesPolyfillLoaderServiceTest {

    private static final String POLYFILLS_PATH = "polyfills";

    @Autowired
    private FinancialTimesPolyfillLoaderService polyfillLoader;

    @Resource(name = "polyfills")
    private Map<String, Polyfill> expectedPolyfills;

    @Test
    public void testNumberOfPolyfillsLoaded() {
        try {
            Map<String, Polyfill> actualPolyfills = polyfillLoader.loadPolyfills(POLYFILLS_PATH);
            assertEquals("Number of expectedPolyfills loaded is incorrect",
                expectedPolyfills.size(), actualPolyfills.size());
        } catch(IOException e) {
            fail("Loading polyfills with correct path should not throw IOException");
        }
    }

    @Test
    public void testPolyfillsLoaded() {
        try {
            Map<String, Polyfill> actualPolyfills = polyfillLoader.loadPolyfills(POLYFILLS_PATH);
            assertEquals("Loaded polyfills are incorrect",
                expectedPolyfills.toString(), actualPolyfills.toString());
        } catch(IOException e) {
            fail("Loading polyfills with correct path should not throw IOException");
        }
    }

    @Test
    public void testLoadingPolyfillsFromAWrongPath() {
        Map<String, Polyfill> actualPolyfills = null;
        try {
            actualPolyfills = polyfillLoader.loadPolyfills("wrong/path");
            fail("Loading polyfills from the wrong path should throw IOException");
        } catch(IOException e) {
            assertNull("Should return null when path doesn't exist", actualPolyfills);
        }
    }
}
