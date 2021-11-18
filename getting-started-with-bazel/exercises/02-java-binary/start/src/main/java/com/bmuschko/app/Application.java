package com.bmuschko.app;

import com.bmuschko.app.config.ConfigurationReader;
import com.bmuschko.app.config.PropertiesConfigurationReader;

import java.util.Map;

public class Application {
    public static void main(String[] args) {
        ConfigurationReader configurationReader = new PropertiesConfigurationReader();
        Map<String, String> configuration = configurationReader.read();

        for (Map.Entry<String, String> entry : configuration.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}