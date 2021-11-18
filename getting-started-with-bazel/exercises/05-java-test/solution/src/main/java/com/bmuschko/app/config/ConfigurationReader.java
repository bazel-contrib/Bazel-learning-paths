package com.bmuschko.app.config;

import java.util.Map;

public interface ConfigurationReader {
    Map<String, String> read();
}