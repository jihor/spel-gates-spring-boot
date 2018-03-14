# SpEL Gates for Spring Boot
A Spring Boot starter for creating small gate-typed beans with SpEL expressions inside

[ ![Download](https://api.bintray.com/packages/jihor/maven/spel-gates-spring-boot/images/download.svg) ](https://bintray.com/jihor/maven/spel-gates-spring-boot/_latestVersion)
[![CircleCI](https://circleci.com/gh/jihor/spel-gates-spring-boot/tree/master.svg?style=shield)](https://circleci.com/gh/jihor/spel-gates-spring-boot/tree/master)

There are Logic Gates, there are Quantum Gates. Let there also be SpEL Gates!

Similar to logic gates, which implement Boolean functions, the SpEL Gates have SpEL expressions at their core.
These expressions can be changed at runtime, allowing to modify the application flow or change business rules.


### Download

##### Gradle
``` 
repositories { 
    jcenter() 
} dependencies { 
    compile group: 'ru.jihor.spel-gates-spring-boot', name: 'spel-gates-spring-boot-starter', version: '<version>' 
    // or 
    // compile 'ru.jihor.spel-gates-spring-boot:spel-gates-spring-boot-starter:<version>' 
    } 
```
##### Maven 
``` 
<dependency> 
    <groupId>ru.jihor.spel-gates-spring-boot</groupId> 
    <artifactId>spel-gates-spring-boot-starter</artifactId> 
    <version>(version)</version> 
    <type>pom</type> 
</dependency> 
```

### Usage
Expressions go to `spring.spelgates` category in key-value format, e.g. (for YAML):
```
spring:
    spelgates:
        booleanSpelGate: "#person.getName().substring(0,1).toUpperCase() == 'A' && #person.getAge() >= 30"
        stringSpelGate1: "sayHello(#name)"
        greetingMessage: "'Hello'"
```
These are then used by the gates bound to specified keys. Returned type must match between the expression and the gate.

### API Description
#### SpelGate class
The gate returning result T must extend `SpelGate<T>` class. The key is specified in constructor, by default the bean name will be used as key.

#### REST API
When using spel-gates-spring-boot-starter, the following REST API is available for controlling the expressions at runtime:
* `/spelgates`, method = `GET` - get list of all expressions in key-value format
* `/spelgates/{key}`, method = `GET` - get expression for key
* `/spelgates/{key}`, method = `POST` - set expression for key

    Accepts:
    - json like ``
{
    "expression": "<expression value>"
}
``

    Returns HTTP 204 on success.

### Demo project
The demo project contains a usage example. Run the DemoApplication and try it out:

1. Let's try out some greetings:
```
$ curl http://localhost:8080/hello/Alex/32
Hello Alex. You are special!
$ curl http://localhost:8080/hello/Mike/32
Hello Mike. You're just another stranger
```
Apparently there is a logic to a greeting :)

2. Let's see, what it is:
```
$ curl http://localhost:8080/spelgates
{
    "stringSpelGate1": "sayHello(#name)",
    "greetingMessage": "'Hello'",
    "booleanSpelGate": "#person.getName().substring(0,1).toUpperCase() == 'A' && #person.getAge() >= 30"
}
```
`booleanSpelGate` decides if the person deserves a special greeting, `stringSpelGate1` invokes the `sayHello()` method, `greetingMessage` contains the greeting message.

3. Get the value for `greetingMessage`:
```
$ curl http://localhost:8080/spelgates/greetingMessage
{
    "expression": "'Hello'"
}
```

4. Let's change it and see if it did in fact change:
```
$ curl -X POST -H "Content-Type: application/json" -d '{"expression": "\'Salut\'"}' http://localhost:8080/spelgates/greetingMessage
$ curl http://localhost:8080/spelgates/greetingMessage
{
    "expression": "'Salut'"
}
```
5. Try it out:
```
$ curl http://localhost:8080/hello/Alex/32
Salut Alex. You are special!
```
6. Now let's change the logic for the special greeting condition, raising the age to 35. In many countries, a citizen must be 35 or older to qualify for the presidency, so this age makes you special :)
```
$ curl -X POST -H "Content-Type: application/json" -d '{"expression": "#person.getName().substring(0,1).toUpperCase() == \'A\' && #person.getAge() >= 35"}' http://localhost:8080/spelgates/booleanSpelGate
```

7: Now our Alex is not special until he is 35 years old:
```
$ curl http://localhost:8080/hello/Alex/32
Salut Alex. You're just another stranger
$ curl http://localhost:8080/hello/Alex/35
Salut Alex. You are special!
```
