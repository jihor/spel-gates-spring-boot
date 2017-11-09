# SpEL Gates for Spring Boot
There are Logic Gates, there are Quantum Gates. Let there also be SpEL Gates!

See example in the demo project. Run the DemoApplication and try it out:
```
$ curl http://localhost:8080/spelgates
{"stringSpelGate1":"sayHello(#name)","stringSpelGate2":"'Hello'","booleanSpelGate":"#person.getName().substring(0,1).toUpperCase() == 'A' && #person.getAge() >= 30"}

$ curl http://localhost:8080/hello/Alex/32
Hello Alex. You are special!

$ curl http://localhost:8080/hello/Mike/32
Hello Mike. You're just another stranger

$ curl http://localhost:8080/spelgates/stringSpelGate2
'Hello'

$ curl -X POST -H "Content-Type: application/json" -d '{"expression": "\'Salut\'"}' http://localhost:8080/spelgates/stringSpelGate2

$ curl http://localhost:8080/spelgates/stringSpelGate2
'Salut'

$ curl http://localhost:8080/hello/Alex/32
Salut Alex. You are special!

$ curl -X POST -H "Content-Type: application/json" -d '{"expression": "#person.getName().substring(0,1).toUpperCase() == \'A\' && #person.getAge() >= 35"}' http://localhost:8080/spelgates/booleanSpelGate

$ curl http://localhost:8080/hello/Alex/32
Salut Alex. You're just another stranger
```