package ru.jihor.spelgates.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.jihor.spelgates.SpelGatesSettings;

import java.util.Map;

/**
 * Basic endpoint for providing information
 *
 * @author jihor (jihor@ya.ru)
 * Created on 2017-11-07
 */
@Endpoint(id = "spelgates")
@RequiredArgsConstructor
public class SpelGatesEndpoint implements ApplicationContextAware {

    private final SpelGatesSettings spelGatesSettings;

    @ReadOperation
    public Map<String, String> getAllExpressions() {
        return spelGatesSettings.getAllExpressionStrings();
    }

    @ReadOperation
    public String getExpression(@Selector String name) {
        return spelGatesSettings.getExpressionString(name);
    }

    @WriteOperation
    public void setExpression(@Selector String name, String expression) {
        spelGatesSettings.setExpressionString(name, expression);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {}
}
