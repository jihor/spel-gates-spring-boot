package ru.jihor.spelgates;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jihor (jihor@ya.ru)
 * Created on 2017-11-07
 */
@Configuration
public class DemoConfig {
    @Bean
    public SpelGatesSettings spelGatesSettings(){
        SpelGatesSettings spelGatesSettings = new SpelGatesSettings();
        spelGatesSettings.setExpressionString("booleanSpelGate", "#person.getName().substring(0,1).toUpperCase() == 'A' && #person.getAge() >= 30");
        spelGatesSettings.setExpressionString("stringSpelGate1", "sayHello(#name)");
        spelGatesSettings.setExpressionString("stringSpelGate2", "'Hello'");
        return spelGatesSettings;
    }

    @Bean
    public SpelGate<String, Boolean> booleanSpelGate(){
        return new SpelGate<>(spelGatesSettings());
    }

    @Bean
    public SpelGate<String, String> stringSpelGate1(){
        return new SpelGate<>(spelGatesSettings());
    }

    @Bean
    public SpelGate<String, String> stringSpelGate2(){
        return new SpelGate<>(spelGatesSettings());
    }
}
