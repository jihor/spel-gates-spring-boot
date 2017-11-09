package ru.jihor.spelgates;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jihor (jihor@ya.ru)
 * Created on 2017-11-07
 */
public class SpelGatesSettings {
    public static final ExpressionParser parser = new SpelExpressionParser();

    private final Map<String, Expression> gateExpressions = new ConcurrentHashMap<>();

    public Expression getExpression(String gateName) {
        return gateExpressions.get(gateName);
    }

    public void setExpression(String gateName, Expression expression) {
        gateExpressions.put(gateName, expression);
    }

    public String getExpressionString(String gateName) {
        Expression expression = getExpression(gateName);
        return expression != null ? expression.getExpressionString() : null;
    }

    public void setExpressionString(String gateName, String expression) {
        setExpression(gateName, parser.parseExpression(expression));
    }

    public Map<String, String> getAllExpressionStrings() {
        Map<String, String> expressionStringsMap = new HashMap<>();
        gateExpressions.forEach((k, v) -> expressionStringsMap.put(k, v.getExpressionString()));
        return expressionStringsMap;
    }
}
