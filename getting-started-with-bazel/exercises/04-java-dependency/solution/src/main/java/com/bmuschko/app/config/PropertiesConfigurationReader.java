package com.bmuschko.app.config;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PropertiesConfigurationReader implements ConfigurationReader {
    @Override
    public Map<String, String> read() {
        FileBasedConfiguration configuration;
        Parameters params = new Parameters();


        try {
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                            .configure(params.properties()
                                    .setFileName("application.properties"));
            configuration = builder.getConfiguration();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> map = new HashMap<>();
        Iterator<String> keys = configuration.getKeys();

        while (keys.hasNext()) {
            String key = keys.next();
            map.put(key, (String) configuration.getProperty(key));
        }

        return map;
    }
}