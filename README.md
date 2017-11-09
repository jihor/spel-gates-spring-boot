# SpEL Gates for Spring Boot
There are Logic Gates, there are Quantum Gates. Let there also be SpEL Gates!

Similar to logic gates, which implement Boolean functions, the SpEL Gates implement SpEL expressions.
These expressions can be changed at runtime, allowing to modify the application flow or change business rules.

### Demo project
Run the DemoApplication and try it out:

1. Let's try out some greetings:
```
$ curl http://localhost:8080/hello/Alex/32
Hello Alex. You are special!
$ curl http://localhost:8080/hello/Mike/32
Hello Mike. You're just another stranger
```
Apparently there is a logic to a greeting.

2. Let's see, what it is:
```
$ curl http://localhost:8080/spelgates
{"stringSpelGate1":"sayHello(#name)","stringSpelGate2":"'Hello'","booleanSpelGate":"#person.getName().substring(0,1).toUpperCase() == 'A' && #person.getAge() >= 30"}
```
`booleanSpelGate` decides if the person deserves a special greeting, `stringSpelGate1` invokes the `sayHello()` method, `stringSpelGate2` contains the greeting message.

3. Get the value for `stringSpelGate2`:
```
$ curl http://localhost:8080/spelgates/stringSpelGate2
'Hello'
```

4. Let's change it and see if it did in fact change:
```
$ curl -X POST -H "Content-Type: application/json" -d '{"expression": "\'Salut\'"}' http://localhost:8080/spelgates/stringSpelGate2
$ curl http://localhost:8080/spelgates/stringSpelGate2
'Salut'
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
