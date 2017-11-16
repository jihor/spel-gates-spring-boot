package ru.jihor.spelgates.autoconfigure;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties("spring")
public class SpelGatesConfigProps {
    private final Map<String, String> spelgates = new HashMap<>();

    public Map<String, String> getSpelgates() {
        return spelgates;
    }
}
