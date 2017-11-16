package ru.jihor.spelgates.endpoints;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import ru.jihor.spelgates.SpelGatesSettings;

import java.util.Map;

/**
 * Basic endpoint for providing information
 *
 * @author jihor (jihor@ya.ru)
 * Created on 2017-11-07
 */
public class SpelGatesEndpoint extends AbstractEndpoint<Map<String, String>> {

    private final SpelGatesSettings spelGatesSettings;

    public SpelGatesEndpoint(SpelGatesSettings spelGatesSettings) {
        super("spelgates", false, true);
        this.spelGatesSettings = spelGatesSettings;
    }

    @Override
    public Map<String, String> invoke() {
        return spelGatesSettings.getAllExpressionStrings();
    }

    public String getExpression(String gateName) {
        return spelGatesSettings.getExpressionString(gateName);
    }

    public void setExpression(String gateName, String expression) {
        spelGatesSettings.setExpressionString(gateName, expression);
    }

}
