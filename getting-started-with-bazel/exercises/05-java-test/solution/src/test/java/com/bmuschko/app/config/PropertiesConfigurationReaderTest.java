package com.bmuschko.app.config;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PropertiesConfigurationReaderTest {
    @Test
    public void testRead() {
        ConfigurationReader configurationReader = new PropertiesConfigurationReader();
        Map<String, String> configuration = configurationReader.read();
        assertEquals(2, configuration.size());
        assertEquals("production", configuration.get("profile"));
        assertEquals("1.0.0", configuration.get("version"));
    }
}