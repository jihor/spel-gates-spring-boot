package ru.jihor.spelgates.autoconfigure;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.jihor.spelgates.endpoints.SpelGatesEndpoint;
import ru.jihor.spelgates.endpoints.mvc.SpelGatesMvcEndpoint;

/**
 * @author jihor (jihor@ya.ru)
 * Created on 2017-11-07
 */
@Configuration
@ConditionalOnClass(Endpoint.class)
@ConditionalOnWebApplication
@AutoConfigureAfter({SpelGatesAutoConfiguration.class})
public class SpelGatesMvcAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SpelGatesMvcEndpoint spelGatesMvcEndpoint(SpelGatesEndpoint spelGatesEndpoint) {
        return new SpelGatesMvcEndpoint(spelGatesEndpoint);
    }

}
