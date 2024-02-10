package com.norwayyachtbrockers.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String ENV_FILE_PATH = "./.env";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Path path = Paths.get(ENV_FILE_PATH);
        if (Files.exists(path)) {
            Map<String, Object> envProperties = loadDotenvProperties(path);
            environment.getPropertySources().addLast(new MapPropertySource("customProperties", envProperties));
        }
        // If .env doesn't exist, it defaults to using environment variables which don't need explicit loading.
    }

    private Map<String, Object> loadDotenvProperties(Path path) {
        Map<String, Object> properties = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                // Skip comments and empty lines
                if (line.trim().isEmpty() || line.trim().startsWith("#")) continue;

                // Assuming each non-empty line is a key=value pair
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    properties.put(key, value);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load .env file", e);
        }
        return properties;
    }
}

