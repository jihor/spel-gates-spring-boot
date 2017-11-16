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
    public SpelGate<Boolean> booleanSpelGate() {
        return new SpelGate<>();
    }

    @Bean
    public SpelGate<String> stringSpelGate1() {
        return new SpelGate<>();
    }

    // example of a bean with custom key
    @Bean
    public SpelGate<String> stringSpelGate2() {
        return new SpelGate<>("greetingMessage");
    }

}
