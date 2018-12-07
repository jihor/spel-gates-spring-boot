package ru.jihor.spelgates.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.jihor.spelgates.SpelGatesSettings;
import ru.jihor.spelgates.endpoints.SpelGatesEndpoint;

/**
 * @author jihor (jihor@ya.ru)
 * Created on 2017-11-07
 */
@Configuration
@ConditionalOnClass(Endpoint.class)
@AutoConfigureAfter({EndpointAutoConfiguration.class})
@EnableConfigurationProperties(SpelGatesConfigProps.class)
public class SpelGatesAutoConfiguration {

    @Autowired
    SpelGatesConfigProps configProps;

    @Bean
    @ConditionalOnMissingBean
    SpelGatesSettings spelGatesSettings() {
        return new SpelGatesSettings(configProps.getSpelgates());
    }

    @Bean
    @ConditionalOnMissingBean
    SpelGatesEndpoint SpelGatesEndpoint(SpelGatesSettings spelGatesSettings) {
        return new SpelGatesEndpoint(spelGatesSettings);
    }
}