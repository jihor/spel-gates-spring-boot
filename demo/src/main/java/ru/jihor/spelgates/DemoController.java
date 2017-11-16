package ru.jihor.spelgates;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jihor (jihor@ya.ru)
 *         Created on 2017-11-07
 */
@RestController
@Slf4j
public class DemoController {

    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String PERSON = "person";

    @Autowired
    private SpelGate<Boolean> booleanSpelGate;

    @Autowired
    private SpelGate<String> stringSpelGate1;

    @Autowired
    private SpelGate<String> stringSpelGate2;

    @RequestMapping(value = "/hello/{name}/{age}")
    public String hello(@PathVariable(NAME) String name, @PathVariable(AGE) Integer age) throws InterruptedException {
        Person person = new Person(name, age);
        log.info(person.toString());
        String msg = stringSpelGate1.evaluate(this, NAME, name) + ". ";
        return msg + (booleanSpelGate.evaluate(this, PERSON, person)? "You are special!" : "You're just another stranger");
    }

    //this method is invoked from stringSpelGate1
    public String sayHello(String name) {
        return stringSpelGate2.evaluate(this) + " " + name;
    }
}
