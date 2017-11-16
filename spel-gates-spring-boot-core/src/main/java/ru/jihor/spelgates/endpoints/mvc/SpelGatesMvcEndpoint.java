package ru.jihor.spelgates.endpoints.mvc;

import lombok.Getter;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.boot.actuate.endpoint.mvc.HypermediaDisabled;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.jihor.spelgates.annotations.ActuatorGetMapping;
import ru.jihor.spelgates.annotations.ActuatorPostMapping;
import ru.jihor.spelgates.endpoints.SpelGatesEndpoint;

import java.util.Collections;
import java.util.Map;

/**
 * MVC Endpoint for returning the basic info
 *
 * @author jihor (jihor@ya.ru)
 * Created on 2017-11-07
 */
@ConfigurationProperties(prefix = "endpoints.spelgates")
public class SpelGatesMvcEndpoint extends EndpointMvcAdapter {
    public static final String NO_EXPRESSION_DEFINED = "No expression defined";
    public static final String EXPRESSION = "expression";
    @Getter
    private final SpelGatesEndpoint delegate;

    public SpelGatesMvcEndpoint(SpelGatesEndpoint delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    // Documentation ( https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc ) says:
    // "The syntax {varName:regex} declares a URI variable with a regular expressions with the syntax {varName:regex}"
    // If there were no [.*] there, this ( https://stackoverflow.com/questions/3526523/spring-mvc-pathvariable-getting-truncated )
    // would've happened
    @ActuatorGetMapping("/{name:.*}")
    @ResponseBody
    @HypermediaDisabled
    public ResponseEntity<?> get(@PathVariable String name) {
        if (!getDelegate().isEnabled()) {
            return getDisabledResponse();
        }
        String expression = getDelegate().getExpression(name);
        String expressionValue = expression != null ? expression : NO_EXPRESSION_DEFINED;
        return ResponseEntity.ok(Collections.singletonMap(EXPRESSION, expressionValue));
    }

    @ActuatorGetMapping("/")
    @ResponseBody
    @HypermediaDisabled
    public ResponseEntity<?> getAll() {
        return getDelegate().isEnabled() ? ResponseEntity.ok(getDelegate().invoke()) : getDisabledResponse();
    }

    @ActuatorPostMapping("/{name:.*}")
    @ResponseBody
    @HypermediaDisabled
    public ResponseEntity<?> set(@PathVariable String name,
                                 @RequestBody Map<String, String> configuration) {
        if (!getDelegate().isEnabled()) {
            return getDisabledResponse();
        }
        try {
            getDelegate().setExpression(name, getExpression(configuration));
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    private String getExpression(Map<String, String> configuration) {
        String value = configuration.get(EXPRESSION);
        if (value == null) {
            throw new IllegalArgumentException("Null expression is not allowed");
        }
        if (value.equalsIgnoreCase(NO_EXPRESSION_DEFINED)) {
            throw new IllegalArgumentException("No cheating!");
        }
        return value;
    }

}
