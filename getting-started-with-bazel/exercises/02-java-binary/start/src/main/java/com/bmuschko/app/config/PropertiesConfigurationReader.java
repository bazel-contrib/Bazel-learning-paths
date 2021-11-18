package com.bmuschko.app.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesConfigurationReader implements ConfigurationReader {
    @Override
    public Map<String, String> read() {
        Properties prop = new Properties();

        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> map = new HashMap<>();

        for (Map.Entry<Object, Object> entry : prop.entrySet()) {
            map.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }

        return map;
    }
}