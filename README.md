# currency-convertor

code base for nosto's currency convertor

## Getting Started

### Install JAVA JDK 8
You can follow this [article](https://www3.ntu.edu.sg/home/ehchua/programming/howto/JDK_Howto.html) to install java.

### Install Maven
You can follow this [article](https://mkyong.com/maven/install-maven-on-mac-osx/) to install maven.

### Install Redis

1. brew update
2. brew install redis

To start redis :
`brew services start redis`
To stop redis :
`brew services stop redis`

### To build and run the project

```
mvn package -DskipTests
mvn spring-boot:run
```
### How to run test
```
Start Redis Server locally and then trigger tests
```

### How to test the conversion API locally

```
http://localhost:8080/api/currency-convertor/from/USD/to/CAD/value/10
```

### Swagger

```
http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
```

### How to test the conversion API online

```
https://nosto-currency-convertor.herokuapp.com/swagger-ui.html
```
##### PS: Right now the caching part is not implemented on the online version

###Health Check

```
http://localhost:8080/actuator/health
```

### Format Code

```
 mvn com.coveo:fmt-maven-plugin:format
```