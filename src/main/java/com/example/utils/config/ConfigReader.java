package com.example.utils.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Optional;

public class ConfigReader {

    private Config config;
    private final Optional<String> environment = Optional.ofNullable(System.getenv("ENVIRONMENT"));

    private static final Config BASE = ConfigFactory.load();

    private ConfigReader(final String setting) {
        String env = environment.orElse("dev");
        config = BASE.getConfig(setting);
        if (config.hasPath(env)) {
            config = config.getConfig(env).withFallback(config).withFallback(config);
        }
    }

    public static ConfigReader getConfigReader(final String setting) {
        return new ConfigReader(setting);
    }

    public String getProperty(final String key) {
        return config.getString(key);
    }
}
