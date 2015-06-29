# daemonizer
Lightweight Spring Boot Application that executes one bash command on startup and another one on sigkill

## Clone and package

```
git clone https://github.com/Predictia/daemonizer.git && cd daemonizer
mvn package
```

## Example run



```
java -jar target/daemonizer-0.0.2.jar
```

(Control+C to kill)

```
java -jar target/daemonizer-0.0.2.jar --command.oninit='echo "my cool daemon"'
```

Full list of configurable parameters: https://github.com/Predictia/daemonizer/blob/master/src/main/resources/application.properties
