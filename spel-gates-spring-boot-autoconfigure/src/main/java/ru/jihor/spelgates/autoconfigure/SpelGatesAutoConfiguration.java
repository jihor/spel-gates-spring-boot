package ru.jihor.spelgates.autoconfigure;

import org.springframework.boot.actuate.autoconfigure.EndpointAutoConfiguration;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.jihor.spelgates.SpelGatesSettings;
import ru.jihor.spelgates.endpoints.SpelGatesEndpoint;

/**
 * @author jihor (jihor@ya.ru)
 *         Created on 2017-11-07
 */
@Configuration
@ConditionalOnClass(Endpoint.class)
@AutoConfigureAfter({ EndpointAutoConfiguration.class })
public class SpelGatesAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    SpelGatesSettings spelGatesSettings(){
        return new SpelGatesSettings();
    }

    @Bean
    @ConditionalOnMissingBean
    SpelGatesEndpoint SpelGatesEndpoint(SpelGatesSettings spelGatesSettings){
        return new SpelGatesEndpoint(spelGatesSettings);
    }
}