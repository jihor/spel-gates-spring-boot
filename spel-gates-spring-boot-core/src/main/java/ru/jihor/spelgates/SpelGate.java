package ru.jihor.spelgates;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.Map;

/**
 * @author jihor (jihor@ya.ru)
 * Created on 2017-11-07
 */
// TODO: Add beans context
// Error was: "No bean resolver registered in the context to resolve access to bean @beanName"
public class SpelGate<T> implements BeanNameAware {

    @Autowired
    private SpelGatesSettings spelGatesSettings;

    @Getter
    @Setter
    private String name;

    public SpelGate() {
        this(null);
    }

    public SpelGate(String gateName) {
        setName(gateName);
    }

    @Override
    public void setBeanName(String name) {
        if (getName() == null) {
            setName(name);
        }
    }

    @PostConstruct
    private void init() {
        if (getExpression() == null) {
            throw new BeanInitializationException(MessageFormat.format("No expression defined for bean [{0}]", getName()));
        }
    }

    public Expression getExpression() {
        return spelGatesSettings.getExpression(getName());
    }

    private StandardEvaluationContext createContext(Object rootObject) {
        return new StandardEvaluationContext(rootObject);
    }

    private StandardEvaluationContext createContext(Object rootObject, String key, Object value) {
        StandardEvaluationContext context = createContext(rootObject);
        context.setVariable(key, value);
        return context;
    }

    private StandardEvaluationContext createContext(Object rootObject, String key1, Object value1, String key2, Object value2) {
        StandardEvaluationContext context = createContext(rootObject, key1, value1);
        context.setVariable(key2, value2);
        return context;
    }

    private StandardEvaluationContext createContext(Object rootObject, String key1, Object value1, String key2, Object value2, String key3, Object value3) {
        StandardEvaluationContext context = createContext(rootObject, key1, value1, key2, value2);
        context.setVariable(key3, value3);
        return context;
    }

    private StandardEvaluationContext createContext(Object rootObject, Map<String, Object> additionalContext) {
        StandardEvaluationContext context = createContext(rootObject);
        context.setVariables(additionalContext);
        return context;
    }

    private T evaluate(StandardEvaluationContext context) {
        return (T) getExpression().getValue(context);
    }

    public T evaluate(Object o) {
        StandardEvaluationContext context = createContext(o);
        return evaluate(context);
    }

    public T evaluate(Object rootObject, String key, Object value) {
        StandardEvaluationContext context = createContext(rootObject, key, value);
        return evaluate(context);
    }

    public T evaluate(Object rootObject, String key1, Object value1, String key2, Object value2) {
        StandardEvaluationContext context = createContext(rootObject, key1, value1, key2, value2);
        return evaluate(context);
    }

    public T evaluate(Object rootObject, String key1, Object value1, String key2, Object value2, String key3, Object value3) {
        StandardEvaluationContext context = createContext(rootObject, key1, value1, key2, value2, key3, value3);
        return evaluate(context);
    }

    public T evaluate(Object rootObject, Map<String, Object> additionalContext) {
        StandardEvaluationContext context = createContext(rootObject, additionalContext);
        return evaluate(context);
    }
}
